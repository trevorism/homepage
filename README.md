# homepage
![Build](https://github.com/trevorism/homepage/actions/workflows/deploy.yml/badge.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/trevorism/homepage)
![GitHub language count](https://img.shields.io/github/languages/count/trevorism/homepage)
![GitHub top language](https://img.shields.io/github/languages/top/trevorism/homepage)
 
[Trevorism.com](https://trevorism.com)

Current Version: 0.8.0

## Development

Install latest node, npm LTS

Run the server on localhost:8080 : `gradle appengineRun`

Run the client server on localhost:3000 : `cd src/app; npm run dev`

Add a LOCALHOST_COOKIES environment variable with a value of true to develop on localhost

Add a `secrets.properties` to src/main/resources with `clientId` and `clientSecret` values
