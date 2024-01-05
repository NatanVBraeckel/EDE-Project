# Zoo microservices | Natan Van Braeckel

Mijn project gaat over het beheer van de essentiële zaken van een dierentuin.\
Men kan het voedsel, de dieren en de behuizingen van de dierentuin beheren.\
Een dier heeft 1 favoriet voedsel, en een behuizing kan verschillende dieren bevatten.

Bij het ophalen van een dier wordt ook het favoriete voedsel ervan opgehaald.\
Bij het ophalen van een behuizing worden ook alle dieren, en ook samen met hun favoriete voedsel, opgehaald.

## Links

Gateway base URL: [https://api-gateway-natanvbraeckel.cloud.okteto.net](https://api-gateway-natanvbraeckel.cloud.okteto.net "https://api-gateway-natanvbraeckel.cloud.okteto.net")\
Dierentuin frontend: [https://ede-zoo-natanvb.netlify.app](https://ede-zoo-natanvb.netlify.app "https://ede-zoo-natanvb.netlify.app")

## Inhoud

1. [Microservices & andere componenten](#microservices--andere-componenten):
    - [Diagram](#diagram)
    - [Lijst](#lijst)
2. [Uitbreidingen](#uitbreidingen)
    - [Kubernetes Manifest files](#kubernetes-manifest-files-5)
    - [Logisch gebruik van ClusterIP en Nodeport](#logisch-gebruik-van-clusterip-en-nodeport-5)
    - [Frontend](#frontend-15)
3. [Werking van de endpoints via postman](#werking-van-de-endpoints-via-postman)
    - [Food service](#food-service)
    - [Animal service](#animal-service)
    - [Enclosure service](#enclosure-service)

## Microservices & andere componenten

### Diagram

![Diagram zoo](./readme-images/diagram.png)

### Lijst

| Component   | Image	| Ports |
|---	|---	|---	|
|  	api-gateway   |   natanvanbraeckel/ede-api-gateway:latest	|   8083:8083 <br> NodePort 30001	|
|   enclosure-service	|   natanvanbraeckel/ede-enclosure-service:latest	|   8080:8080	|
|   animal-service	|   natanvanbraeckel/ede-animal-service:latest	|   8082:8082	|
|   food-service	|   natanvanbraeckel/ede-food-service:latest	|   8081:8081	|
|   mongo-enclosure	|   mongo:latest	|   27017:27017	|
|   animal-service	|   mysql:latest	|   3307:3306	|
|   food-service	|   mysql:latest	|   3306:3306	|

## Uitbreidingen

### Kubernetes Manifest files (+5%)

Ik heb mijn docker-compose.yml file omgezet in Kubernetes Manifest yaml files.

Ik heb er dan 1 yaml file per component van gemaakt, waarin de service, deployment en eventuele volume bij elkaar worden geplaatst. Ze staan in de kubernets folder, zo zal Okteto ze herkennen.

### Logisch gebruik van ClusterIP en NodePort (+5%)

Enkel api-gateway.yaml maakt gebruik van een NodePort. Enkel de api-gateway zal dus beschikbaar zijn buiten het netwerk. Zo kunnen externe gebruikers enkel data opvragen via de gateway, en zullen ze dus ook via de authenticatie moeten.

![Diagram zoo](./readme-images/gateway-service-yaml.png)

De andere microservices gebruiken dus de default type ClusterIP, zodat ze met elkaar kunnen communiceren.

### Frontend (+15%)

Dierentuin frontend: [https://ede-zoo-natanvb.netlify.app](https://ede-zoo-natanvb.netlify.app "https://ede-zoo-natanvb.netlify.app")

Mijn frontend is gemaakt met React, en maakt het beheren van alle onderdelen van de dierentuin mogelijk.

*Food*:\
Een niet ingelogde gebruiker kan de food items zien.\
Een ingelogde kan bestaande food items bewerken, de stock van een food item verhogen of verminderen, nieuwe food items aanmaken, of bestaande food items verwijderen.
![Frontend food](./readme-images/frontend-food.png)

*Animal*:\
Een niet ingelogde gebruiker kan de animals zien.\
Een ingelogde kan bestaande animals bewerken, nieuwe animals aanmaken, of bestaande animals verwijderen.
![Frontend animal](./readme-images/frontend-animal.png)

*Enclosure*:\
Een niet ingelogde gebruiker kan de enclosures zien.\
Een ingelogde kan bestaande enclosures bewerken, nieuwe enclosures aanmaken, of bestaande enclosures verwijderen.
![Frontend enclosure](./readme-images/frontend-enclosure.png)

## Werking van de endpoints (via postman)

### Authenticatie

Alle GET requests worden doorgelaten zonder dat er authenticatie nodig is.\
Bij een CREATE, PUT of DELETE request zal men zich dus moeten authentificeren via Google Cloud OAuth2.

Dit kan door in Postman een token aan te vragen, en dan de id_token mee te geven als Bearer token.

![Token](./readme-images/token.png)

### Food service

#### All food

![Food all](./readme-images/food-all.png)

#### Food by id

![Food by id](./readme-images/food-id.png)

#### Food by id (list)

![Food by id list](./readme-images/food-id-list.png)

#### Food by food code

![Food by code](./readme-images/food-code.png)

#### Food by food code (list)

![Food by code](./readme-images/food-code-list.png)

#### Create food (auth)

![Food create](./readme-images/food-create.png)

#### Update food (auth)

![Food update](./readme-images/food-update.png)

#### Update stock (increase) (auth)

![Food stock increase](./readme-images/food-stock-pos.png)

#### Update stock (decrease) (auth)

![Food stock decrease](./readme-images/food-stock-neg.png)

#### Delete food (auth)

![Food delete](./readme-images/food-delete.png)

### Animal service

#### All animals

![Animal all](./readme-images/animal-all.png)

#### Animal by id 

![Animal by id](./readme-images/animal-id.png)

#### Animal by id (list)

![Animal by id (list)](./readme-images/animal-id-list.png)

#### Animal by animal code

![Animal by code](./readme-images/animal-code.png)

#### Animal by animal code (list)

![Animal by code (list)](./readme-images/animal-code-list.png)

#### Create animal (auth)

![Animal create](./readme-images/animal-create.png)

#### Update animal (auth)

![Animal update](./readme-images/animal-update.png)

#### Delete animal (auth)

![Animal delete](./readme-images/animal-delete.png)

### Enclosure service

#### All enclosures

![Enclosure all](./readme-images/enclosure-all.png)

#### Enclosure by id

![Enclosure by id](./readme-images/enclosure-id.png)

#### Enclosure by id (list)

![Enclosure by id (list)](./readme-images/enclosure-id-list.png)

#### Enclosure by enclosure code

![Enclosure by code](./readme-images/enclosure-code.png)

#### Enclosure by enclosure code (list)

![Enclosure by code (list)](./readme-images/enclosure-code-list.png)

#### Create enclosure (auth)

![Enclosure create](./readme-images/enclosure-create.png)

#### Update enclosure (auth)

![Enclosure update](./readme-images/enclosure-update.png)

#### Delete enclosure (auth)

![Enclosure delete](./readme-images/enclosure-delete.png)