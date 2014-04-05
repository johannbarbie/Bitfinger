# Bitfinger


[Bitfinger.org](https://bitfinger.rog) is a lookup service for bitcoin addresses. It is based on [Webfinger](https://tools.ietf.org/html/rfc7033), a micro-format to describe human readable identifiers like account@host.org.

## Instruction

The solution can be used in many ways, with CORS headers and DKIM signature provided. These instructions utilize *Dropbox* anid *Gmail* for a simple setup. 

To publish your bitcoin address following the Webfinger standard you need to follow these steps:

### 1. Create a file 
Create a file called **webfinger.js** in your Dropbox Public folder with this content:
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
  make sure to replace email and bitcoin address in the example with yours.
### 2. Copy URL
Copy the public URL of this file. It should start like this ```'https://dl.dropboxusercontent.com/...'``` and end with ```.js```. Make sure it's not just the web link, but the actual file.
### 3. Send Email
Send an email from your Gmail account to **fist@webfist.org** with this content:
  ```
  webfist = https://dl.dropboxusercontent.com/u/accountid/webfinger.js
  ```
Replace the link with the link from step 2.
### 4. Check
Check http://webfist.org/ with your Gmail address and see if you can get a positive response.

Check https://bitfinger.org and see if you can get the bitcoin address back as a QR code.

License
----

MIT
