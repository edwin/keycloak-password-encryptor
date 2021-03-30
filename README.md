# How to Encrypt and Decrypt Password on Keycloak

This SPI (Service Provider Interfaces) plugins provides the functionality of doing encryption for sensitive data (password field for example) when being transported thru network. 

This is needed despite that most of the time messages are being transported in an SSL network, but sometimes we have SSL offloading along the way and exposing the risk of  people seeing transported password in a plain text. Therefore for this case, an encryption is needed at the browser's end, and the same decryption will be needed at Keycloak's end in order so that Keycloak can read the password accordingly.   
## How to Build
```
mvn clean package
```

## Version
```
Keycloak 4.8.3
Red Hat Single Sign-On 7.3
```

## Blog Post
```
https://edwin.baculsoft.com/2021/03/how-to-encrypt-and-decrypt-password-on-keycloak-or-red-hat-sso/
```