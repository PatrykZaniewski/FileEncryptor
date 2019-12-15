Scenario: Test AES ECB algorithm on .txt file
Given a /src/test/bdd/loremCipher.txt file
When I choose the AES algorithm with ECB mode and abc password
Then I get the proper cipheredText (nFSKRcSC/DPI5Q8MTMX0g/pKo2GZ9TYJMeFVqHtCOTQ=)

Scenario: Test AES CBC algorithm on .txt file
Given a /src/test/bdd/loremCipher.txt file
When I choose the AES algorithm with CBC mode and abc password and Uw7EzaO5V2fae5OcU8BNyg== iv
Then I get the proper cipheredText (DBV4axE7eSJ9dm7hjOR6W0f8jNgFAiHWgkOzUkcluYY=)

Scenario: Test AES CFB algorithm on .txt file
Given a /src/test/bdd/loremCipher.txt file
When I choose the AES algorithm with CFB mode and abc password and Uw7EzaO5V2fae5OcU8BNyg== iv
Then I get the proper cipheredText (4HDPzhZJn/PAzwCz3JOhssLvkL2rmQhVXlQ23xN452E=)

Scenario: Test DES ECB algorithm on .txt file
Given a /src/test/bdd/loremCipher.txt file
When I choose the DES algorithm with ECB mode and abc password
Then I get the proper cipheredText (StfeD3aDPTJDhq18vUdU4wy9XzDau3PKBJxcOvWFakA=)

Scenario: Test DES CBC algorithm on .txt file3
Given a /src/test/bdd/loremCipher.txt file
When I choose the DES algorithm with CBC mode and abc password and cGR0Z9YIYg0= iv
Then I get the proper cipheredText (LbfX6Zg7qkcodmlWi1s9YW0b2EqURalpMPPlGqg9d6E=)
