# Android Map Samples

## Features

This repository contains samples for the following features:
* [AirMapView](https://github.com/airbnb/AirMapView)
* Polygons and GeoJSONs
* Heatmaps
* Offline reverse geocoding
* Yelp and Foursquare APIs
* [ParisData](http://opendata.paris.fr) datasets

Warning: this code is NOT intended for production purposes. For example, there are I/O operations on the main thread

## How to use

This project expects a `secrets.properties` file which should contain your different API keys.
It should look like that:

```properties
GOOGLE_MAPS_KEY=...
FOURSQUARE_CLIENT_ID=...
FOURSQUARE_CLIENT_SECRET=...
YELP_CONSUMER_KEY=...
YELP_CONSUMER_SECRET=...
YELP_TOKEN=...
YELP_TOKEN_SECRET=...
```


