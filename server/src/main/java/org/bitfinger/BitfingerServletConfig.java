package org.bitfinger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.apache.shiro.guice.web.GuiceShiroFilter;
import org.bitfinger.pojo.Subscribe;
import org.restnucleus.log.SLF4JTypeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;



public class BitfingerServletConfig extends GuiceServletContextListener {
	public static String basePath;
	public static String resPath;
	public static String svcPath;
	public static String svcUser;
	public static String svcPw;
	public static String captchaPubKey;
	public static String captchaSecKey;
	public static Logger log = LoggerFactory.getLogger(BitfingerServletConfig.class);
	public static Injector injector;
	public static SocketIOServer server;
	static {
		basePath = System.getProperty("basePath");
		resPath = System.getProperty("resPath");
		captchaPubKey = System.getProperty("captchaPubKey");
		captchaSecKey = System.getProperty("captchaSecKey");
		svcPath = System.getProperty("svcPath");
		svcUser = System.getProperty("svcUser");
		svcPw = System.getProperty("svcPw");
	}
	
	private ServletContext servletContext;
    
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
		final Injector i = getInjector();
		server = i.getInstance(SocketIOServer.class);
		final Cache cache = i.getInstance(Cache.class);
		server.addJsonObjectListener(Subscribe.class, new DataListener<Subscribe>() {
	        @Override
	        public void onData(final SocketIOClient client, final Subscribe data, AckRequest ackRequest) {
	        	//TODO: validate phone is valid format
	        	client.joinRoom(data.getRoom());
	        	Element e = cache.get(data.getRoom());
	        	if (null == e || e.isExpired()){
	        		new AddressRequestor(data.getRoom(),server).start();
	        	}else{
	        		String address = (String)e.getObjectValue();
	        		server.getRoomOperations(data.getRoom()).sendEvent("found", address);
	        	}
	        }
	    });
		server.start();
		log.info("ServletContextListener started");
	}
	
    @Override
    protected Injector getInjector(){
        injector = Guice.createInjector(new ServletModule(){
            @Override
            protected void configureServlets(){
            	filter("/*").through(CorsFilter.class);
            	filter("/*").through(GuiceShiroFilter.class);
            	bindListener(Matchers.any(), new SLF4JTypeListener());
        	}
            
			@Provides @Singleton @SuppressWarnings("unused")
			public SocketIOServer provideSocket(){
			 	Configuration config = new Configuration();
			    config.setPort(8081);
			    SocketIOServer server = new SocketIOServer(config);
			    return server;
			}
			        
        	@Provides @Singleton @SuppressWarnings("unused")
        	public Cache provideCache(){
        		//Create a singleton CacheManager using defaults
        		CacheManager manager = CacheManager.create();
        		//Create a Cache specifying its configuration.
        		Cache testCache = new Cache(new CacheConfiguration("cache", 1000)
        		    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
        		    .eternal(false)
        		    .timeToLiveSeconds(7200)
        		    .timeToIdleSeconds(3600)
        		    .diskExpiryThreadIntervalSeconds(0));
        		  manager.addCache(testCache);
        		  return testCache;
        	}},new BitfingerShiroWebModule(this.servletContext));
        return injector;
    }
	
    @Override
	public void contextDestroyed(ServletContextEvent sce) {
    	server.stop();
		super.contextDestroyed(sce);
		log.info("ServletContextListener destroyed");
	}

}
