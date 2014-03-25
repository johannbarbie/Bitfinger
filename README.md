Bitfinger
=========

Bitfinger is a lookup service for bitcoin addresses. It is based on [Webfinger](https://tools.ietf.org/html/rfc7033), a micro-format to describe human readable identifiers like account@host.org.

Instruction
----

The solution can be used in many ways, with CORS headers and DKIM signature provided. These instructions utilize *Dropbox* anid *Gmail* for a simple setup. 

To publish your bitcoin address following the Webfinger standard you need to follow these steps:

1. create a file called **webfinger.js** in your Dropbox Public folder.
```json
{
  "subject": "acct:makingabetter@gmail.com",
  "links":
  [
    {
      "rel": "bitcoin",
      "href": "bitcoin:19xeDDxhahx4f32WtBbPwFMWBq28rrYVoh"
    }
  ]
}
```
make sure to replace email and bitcoin address.
2. Copy the public URL of this file
3. Send an email from Gmail to **fist@webfist.org** with this content, replacing the link with the link from step 2:
```
webfist = https://dl.dropboxusercontent.com/u/accountid/webfinger.js
```
check http://webfist.org/ for details.
5. check http://webfist.org/ with your email address and see if you can get a positive response for your email.
4. check https://bitfinger.org and see if you can get the bitcoin address back an a QR code.

License
----

MIT
