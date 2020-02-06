# File encryptor

Application built for "Software testing".

# Technology:

- Java 8
- Apache Maven
- Json

# Description:

This application has 2 main features:

Encryption/Decryption

There are 5 ciphers that are used to encrpyt data: AES, DES, Blowfish, RC2 and ROT-13. Four of them work in 3 possible modes: CBC, ECB, CBF.
User chooses file to encrypt, cipher, initialization vector (if needed) and password. Then, after clicking "encrypt" button, user receives *.json file with encrypted file and basic data about encryption.
Decryption is even easier because all the user has to do is to choose the encrypted *.json and type password in password field.

Checksum 

Application also provides generating checksums using MD5 or SHA-1. User chooses file, algorithm and clicks "calculate" button. As a response, user receives the calculated checksum.

# Testing

Application was developed using TDD (test driven development) which means that we had written tests before we started develop process.

Later, in next phase we also decided to use BDD (behave driven development).

# Running an application

To run it, just go to the main directory and type:

    mvn clean compile package
    
Then, double click on *.jar file and after a while feel free to use the app.

# Authors

- [Jakub Celuch](https://github.com/JCeluch)
- [Łukasz Kamiński](https://github.com/patrykzaniewski)
- [Maciej Kozłowski](https://github.com/lukitoki1)
- [Daniel Sporysz](https://github.com/DanielSporysz)
- [Katarzyna Wolska](https://github.com/KatarzynaWolska)
- [Patryk Zaniewski](https://github.com/patrykzaniewski)
