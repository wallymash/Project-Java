Vi har brukt Spring Boot til å implementere rest service. Serveren vi har brukt er en TomCat som kjører på <localhost:8080>.
Base endpointen er <localhost:8080/bank>.

# Metoder:
### GET: 
*Henter hele banken fra serveren.* 

- URL: http://localhost:8080/bank.
- Parameter: None
- Tilgjengelig: TomCat, Spring Boot
- Returnerer: Json streng med hele innholdet fra serveren
 ```json
  {
    "name" : "Norges Bank",  
    "lastUsedAccountNumber" : 12040000000,  
    "users" : []  
  }
```

 ### POST: 
 *Legger til en ny bruker.*

- URL: http://localhost:8080/bank
- Tilgjengelig: TomCat, Spring Boot
- Parameter: body  application/json; charset=UTF-8

 ```json
  {
    "socialSecurityNumber" : 12345678901,
    "password" : "123sss",
    "accounts" : [ {
      "accountName" : "User account",
      "accountNumber" : 12040000001,
      "balance" : 2000.0
    }, {
      "accountName" : "saving account",
      "accountNumber" : 12040000002,
      "balance" : 1000.0
    } ],
    "transactionHistory" : [ ]
  } 
```

### GET: 
*Henter en bruker fra serveren.* 

- URL: <http://localhost:8080/bank/{SSN}>
- Parameter: Fødsels- og personnummer (SSN)
- Tilgjengelig: TomCat, Spring Boot
- Returnerer: En bruker serialisert til Json streng. Hvis brukeren ikke finnes retunerer den null.

 ```json
  {
    "socialSecurityNumber" : 12345678901,
    "password" : "123sss",
    "accounts" : [ {
      "accountName" : "User account",
      "accountNumber" : 12040000001,
      "balance" : 2000.0
    }, {
      "accountName" : "saving account",
      "accountNumber" : 12040000002,
      "balance" : 1000.0
    } ],
    "transactionHistory" : []
  } 
```

### PUT: 
*Setter inn {pengemengde} mengde med penger til {kontonummer}.* 

- URL: <http://localhost:8080/bank/account{accountNumber}/deposit/{amount}>
- Tilgjengelig: TomCat, Spring Boot
- Parameter: Kontonummer, pengemengde


### PUT: 
*Tar ut {pengemengde} mengde med penger fra {kontonummer}.* 

- URL: <http://localhost:8080/bank/account{kontonummer}/withdraw/{pengemengde}>
- Tilgjengelig: TomCat, Spring Boot
- Parameter: Kontonummer, pengemengde

### PUT: 
*Oppdaterer en eksisterende bruker. Overfører fra en konto til en annen* 

- URL: <http://localhost:8080/bank/account/from/{kontonummer1}/to{kontonummer2}/transfer/{pengemengde}>
- Tilgjengelig: TomCat, Spring Boot
- Parameter: Kontonummer1, kontonummer2, pengemengde
### DELETE
- Delete: Sletter en bruker.
  - URL: <http://localhost:8080/bank/user/{SSN}>
  - Parameter: Fødsels- og personnummer
  - Tilgjengelig: TomCat, Spring Boot

### PUT: 
*Oppdaterer en eksisterende bruker. legger til rente* 

- URL: <http://localhost:8080/bank/addintrest/{kontonummer}>
- Tilgjengelig: TomCat, Spring Boot
- Parameter: Kontonummer