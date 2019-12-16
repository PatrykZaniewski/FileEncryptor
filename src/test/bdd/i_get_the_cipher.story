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

Scenario: Test RC2 ECB algorithm on .txt file
Given a /src/test/bdd/loremCipher.txt file
When I choose the RC2 algorithm with ECB mode and abc password
Then I get the proper cipheredText (FMn96Wx6LTGos0Yf2OmUwACgNdCr1r9r8j5xzoEAKi4=)

Scenario: Test RC2 CBC algorithm on .txt file
Given a /src/test/bdd/loremCipher.txt file
When I choose the RC2 algorithm with CBC mode and abc password and vTQ2awXO7qo= iv
Then I get the proper cipheredText (WdKxcGhsjPKHWM3LckhW2Hq3BDlXCF9UAnhVQ5uqLdM=)

Scenario: Test RC2 CFB algorithm on .txt file
Given a /src/test/bdd/loremCipher.txt file
When I choose the RC2 algorithm with CFB mode and abc password and MyhZw5ZkKKY= iv
Then I get the proper cipheredText (LRoV+o8KV45tuEO5M48cLNstyLJfOGQf40DNzfdG0Y0=)

Scenario: Test ROT algorithm on .txt file
Given a /src/test/bdd/loremCipher.txt file
When I choose the ROT algorithm with 101 shift
Then I get the proper cipheredText (sdTXytKFztXY2tKFydTR1NeF2M7ZhcbSytmT)

Scenario: Test Blowfish CBC algorithm on .txt file3
Given a /src/test/bdd/loremCipher.txt file
When I choose the Blowfish algorithm with CBC mode and abc password and cGR0Z9YIYg0= iv
Then I get the proper cipheredText (TkQz7mdmihGEY6diua5Ba7iehvlbxWdhtxBnlUGHgY8=)

Scenario: Test Blowfish ECB algorithm on .txt file3
Given a /src/test/bdd/loremCipher.txt file
When I choose the Blowfish algorithm with ECB mode and abc password and cGR0Z9YIYg0= iv
Then I get the proper cipheredText (2ISUcy+YVYKu4s5OFdqb+R5RJb62cYiCp9ZNq6EloBo=)

Scenario: Test Blowfish CFB algorithm on .txt file3
Given a /src/test/bdd/loremCipher.txt file
When I choose the Blowfish algorithm with CFB mode and abc password and cGR0Z9YIYg0= iv
Then I get the proper cipheredText (c0seOGnp2Ow3sgD3e1EfnkIqGNq3Nwqc3dDwISZsKMU=)