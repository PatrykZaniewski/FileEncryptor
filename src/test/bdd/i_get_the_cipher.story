Scenario: Test AES CBC algorithm on .txt file
Given a /src/test/bdd/loremCipher.txt file
When I choose the AES algorithm with CBC mode and abc password and Uw7EzaO5V2fae5OcU8BNyg== iv
Then I get the proper cipheredText (nFSKRcSC\/DPI5Q8MTMX0g\/pKo2GZ9TYJMeFVqHtCOTQ=",")






