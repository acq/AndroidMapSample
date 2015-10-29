package com.google.maps.android.geojson;

import com.airbnb.android.airmapview.AirMapMarker;
import com.airbnb.android.airmapview.AirMapPolygon;
import com.airbnb.android.airmapview.AirMapPolyline;
import com.airbnb.android.airmapview.AirMapView;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Renders GeoJsonFeature objects onto the GoogleMap as Marker, Polyline and Polygon objects. Also
 * removes GeoJsonFeature objects and redraws features when updated.
 */
/* package */ class GeoJsonRenderer implements Observer {

    private final static int POLYGON_OUTER_COORDINATE_INDEX = 0;

    private final static int POLYGON_INNER_COORDINATE_INDEX = 1;

    private final static Object FEATURE_NOT_ON_MAP = null;

    /**
     * Value is a Marker, Polyline, Polygon or an array of these that have been created from the
     * corresponding key
     */
    private final HashMap<GeoJsonFeature, Object> mFeatures;

    private final GeoJsonPointStyle mDefaultPointStyle;

    private final GeoJsonLineStringStyle mDefaultLineStringStyle;

    private final GeoJsonPolygonStyle mDefaultPolygonStyle;

    private boolean mLayerOnMap;

    private AirMapView mMap;

    /**
     * Creates a new GeoJsonRender object
     *
     * @param map map to place GeoJsonFeature objects on
     */
    /* package */ GeoJsonRenderer(AirMapView map, HashMap<GeoJsonFeature, Object> features) {
        mMap = map;
        mFeatures = features;
        mLayerOnMap = false;
        mDefaultPointStyle = new GeoJsonPointStyle();
        mDefaultLineStringStyle = new GeoJsonLineStringStyle();
        mDefaultPolygonStyle = new GeoJsonPolygonStyle();

        // Add default styles to features
        for (GeoJsonFeature feature : getFeatures()) {
            setFeatureDefaultStyles(feature);
        }
    }

    /**
     * Given a Marker, Polyline, Polygon or an array of these and removes it from the map
     *
     * @param mapObject map object or array of map objects to remove from the map
     */
    private static void removeFromMap(Object mapObject) {
        if (mapObject instanceof Marker) {
            ((Marker) mapObject).remove();
        } else if (mapObject instanceof Polyline) {
            ((Polyline) mapObject).remove();
        } else if (mapObject instanceof Polygon) {
            ((Polygon) mapObject).remove();
        } else if (mapObject instanceof ArrayList) {
            for (Object mapObjectElement : (ArrayList) mapObject) {
                removeFromMap(mapObjectElement);
            }
        }
    }

    /* package */ boolean isLayerOnMap() {
        return mLayerOnMap;
    }

    /**
     * Gets the GoogleMap that GeoJsonFeature objects are being placed on
     *
     * @return GoogleMap
     */
    /* package */ AirMapView getMap() {
        return mMap;
    }

    /**
     * Changes the map that GeoJsonFeature objects are being drawn onto. Existing objects are
     * removed from the previous map and drawn onto the new map.
     *
     * @param map GoogleMap to place GeoJsonFeature objects on
     */
    /* package */ void setMap(AirMapView map) {
        for (GeoJsonFeature feature : getFeatures()) {
            redrawFeatureToMap(feature, map);
        }
    }

    /**
     * Adds all of the stored features in the layer onto the map if the layer is not already on the
     * map.
     */
    /* package */ void addLayerToMap() {
        if (!mLayerOnMap) {
            mLayerOnMap = true;
            for (GeoJsonFeature feature : getFeatures()) {
                addFeature(feature);
            }
        }
    }

    /**
     * Gets a set containing GeoJsonFeatures
     *
     * @return set containing GeoJsonFeatures
     */
    /* package */ Set<GeoJsonFeature> getFeatures() {
        return mFeatures.keySet();
    }

    /**
     * Checks for each style in the feature and adds a default style if none is applied
     *
     * @param feature feature to apply default styles to
     */
    private void setFeatureDefaultStyles(GeoJsonFeature feature) {
        if (feature.getPointStyle() == null) {
            feature.setPointStyle(mDefaultPointStyle);
        }
        if (feature.getLineStringStyle() == null) {
            feature.setLineStringStyle(mDefaultLineStringStyle);
        }
        if (feature.getPolygonStyle() == null) {
            feature.setPolygonStyle(mDefaultPolygonStyle);
        }
    }

    /**
     * Adds a new GeoJsonFeature to the map if its geometry property is not null.
     *
     * @param feature feature to add to the map
     */
    /* package */ void addFeature(GeoJsonFeature feature) {
        Object mapObject = FEATURE_NOT_ON_MAP;
        setFeatureDefaultStyles(feature);
        if (mLayerOnMap) {
            feature.addObserver(this);

            if (mFeatures.containsKey(feature)) {
                // Remove current map objects before adding new ones
                removeFromMap(mFeatures.get(feature));
            }

            if (feature.hasGeometry()) {
                // Create new map object
                mapObject = addFeatureToMap(feature, feature.getGeometry());
            }
        }
        mFeatures.put(feature, mapObject);
    }

    /**
     * Removes all GeoJsonFeature objects stored in the mFeatures hashmap from the map
     */
    /* package */ void removeLayerFromMap() {
        if (mLayerOnMap) {
            for (GeoJsonFeature feature : mFeatures.keySet()) {
                removeFromMap(mFeatures.get(feature));

                feature.deleteObserver(this);
            }
            mLayerOnMap = false;
        }
    }

    /**
     * Removes a GeoJsonFeature from the map if its geometry property is not null
     *
     * @param feature feature to remove from map
     */
    /* package */ void removeFeature(GeoJsonFeature feature) {
        // Check if given feature is stored
        if (mFeatures.containsKey(feature)) {
            removeFromMap(mFeatures.remove(feature));
            feature.deleteObserver(this);
        }
    }

    /**
     * Gets the default style used to render GeoJsonPoints
     *
     * @return default style used to render GeoJsonPoints
     */
    /* package */ GeoJsonPointStyle getDefaultPointStyle() {
        return mDefaultPointStyle;
    }

    /**
     * Gets the default style used to render GeoJsonLineStrings
     *
     * @return default style used to render GeoJsonLineStrings
     */
    /* package */ GeoJsonLineStringStyle getDefaultLineStringStyle() {
        return mDefaultLineStringStyle;
    }

    /**
     * Gets the default style used to render GeoJsonPolygons
     *
     * @return default style used to render GeoJsonPolygons
     */
    /* package */ GeoJsonPolygonStyle getDefaultPolygonStyle() {
        return mDefaultPolygonStyle;
    }

    /**
     * Adds a new object onto the map using the GeoJsonGeometry for the coordinates and the
     * GeoJsonFeature for the styles.
     *
     * @param feature  feature to get geometry style
     * @param geometry geometry to add to the map
     */
    private Object addFeatureToMap(GeoJsonFeature feature, GeoJsonGeometry geometry) {
        String geometryType = geometry.getType();
        if (geometryType.equals("Point")) {
            return addPointToMap(feature.getPointStyle(), (GeoJsonPoint) geometry);
        } else if (geometryType.equals("LineString")) {
            return addLineStringToMap(feature.getLineStringStyle(),
                    (GeoJsonLineString) geometry);
        } else if (geometryType.equals("Polygon")) {
            return addPolygonToMap(feature.getPolygonStyle(),
                    (GeoJsonPolygon) geometry);
        } else if (geometryType.equals("MultiPoint")) {
            return addMultiPointToMap(feature.getPointStyle(),
                    (GeoJsonMultiPoint) geometry);
        } else if (geometryType.equals("MultiLineString")) {
            return addMultiLineStringToMap(feature.getLineStringStyle(),
                    ((GeoJsonMultiLineString) geometry));
        } else if (geometryType.equals("MultiPolygon")) {
            return addMultiPolygonToMap(feature.getPolygonStyle(),
                    ((GeoJsonMultiPolygon) geometry));
        } else if (geometryType.equals("GeometryCollection")) {
            return addGeometryCollectionToMap(feature,
                    ((GeoJsonGeometryCollection) geometry).getGeometries());
        }
        return null;
    }

    /**
     * Adds a GeoJsonPoint to the map as a Marker
     *
     * @param pointStyle contains relevant styling properties for the Marker
     * @param point      contains coordinates for the Marker
     * @return Marker object created from the given GeoJsonPoint
     */
    private AirMapMarker<GeoJsonPoint> addPointToMap(GeoJsonPointStyle pointStyle, GeoJsonPoint point) {
        AirMapMarker.Builder<GeoJsonPoint> builder = pointStyle.toMarker();
        builder.position(point.getCoordinates());
        AirMapMarker<GeoJsonPoint> marker = builder.build();
        mMap.addMarker(marker);
        return marker;
    }

    /**
     * Adds all GeoJsonPoint objects in GeoJsonMultiPoint to the map as multiple Markers
     *
     * @param pointStyle contains relevant styling properties for the Markers
     * @param multiPoint contains an array of GeoJsonPoints
     * @return array of Markers that have been added to the map
     */
    private ArrayList<AirMapMarker<GeoJsonPoint>> addMultiPointToMap(GeoJsonPointStyle pointStyle,
                                                 GeoJsonMultiPoint multiPoint) {
        ArrayList<AirMapMarker<GeoJsonPoint>> markers = new ArrayList<>(multiPoint.getPoints().size());
        for (GeoJsonPoint geoJsonPoint : multiPoint.getPoints()) {
            markers.add(addPointToMap(pointStyle, geoJsonPoint));
        }
        return markers;
    }

    /**
     * Adds a GeoJsonLineString to the map as a Polyline
     *
     * @param lineStringStyle contains relevant styling properties for the Polyline
     * @param lineString      contains coordinates for the Polyline
     * @return Polyline object created from given GeoJsonLineString
     */
    private AirMapPolyline<GeoJsonLineString> addLineStringToMap(GeoJsonLineStringStyle lineStringStyle,
                                        GeoJsonLineString lineString) {
        AirMapPolyline.Builder<GeoJsonLineString> polylineOptions = lineStringStyle.toPolyline();
        // Add coordinates
        polylineOptions.addAll(lineString.getCoordinates());
        AirMapPolyline<GeoJsonLineString> polyline = polylineOptions.build();
        mMap.addPolyline(polyline);
        return polyline;
    }

    /**
     * Adds all GeoJsonLineString objects in the GeoJsonMultiLineString to the map as multiple
     * Polylines
     *
     * @param lineStringStyle contains relevant styling properties for the Polylines
     * @param multiLineString contains an array of GeoJsonLineStrings
     * @return array of Polylines that have been added to the map
     */
    private ArrayList<AirMapPolyline<GeoJsonLineString>> addMultiLineStringToMap(GeoJsonLineStringStyle lineStringStyle,
                                                        GeoJsonMultiLineString multiLineString) {
        ArrayList<AirMapPolyline<GeoJsonLineString>> polylines = new ArrayList<>();
        for (GeoJsonLineString geoJsonLineString : multiLineString.getLineStrings()) {
            polylines.add(addLineStringToMap(lineStringStyle, geoJsonLineString));
        }
        return polylines;
    }

    /**
     * Adds a GeoJsonPolygon to the map as a Polygon
     *
     * @param polygonStyle contains relevant styling properties for the Polygon
     * @param polygon      contains coordinates for the Polygon
     * @return Polygon object created from given GeoJsonPolygon
     */
    private AirMapPolygon addPolygonToMap(GeoJsonPolygonStyle polygonStyle, GeoJsonPolygon polygon) {
        AirMapPolygon.Builder builder = polygonStyle.toPolygon();
        // First array of coordinates are the outline
        builder.addAll(polygon.getCoordinates().get(POLYGON_OUTER_COORDINATE_INDEX));
        // Following arrays are holes
        for (int i = POLYGON_INNER_COORDINATE_INDEX; i < polygon.getCoordinates().size();
             i++) {
            builder.addHole(polygon.getCoordinates().get(i));
        }
        AirMapPolygon mapPolygon = builder.build();
        mMap.addPolygon(mapPolygon);
        return mapPolygon;
    }

    /**
     * Adds all GeoJsonPolygon in the GeoJsonMultiPolygon to the map as multiple Polygons
     *
     * @param polygonStyle contains relevant styling properties for the Polygons
     * @param multiPolygon contains an array of GeoJsonPolygons
     * @return array of Polygons that have been added to the map
     */
    private ArrayList<AirMapPolygon> addMultiPolygonToMap(GeoJsonPolygonStyle polygonStyle,
                                                    GeoJsonMultiPolygon multiPolygon) {
        ArrayList<AirMapPolygon> polygons = new ArrayList<>();
        for (GeoJsonPolygon geoJsonPolygon : multiPolygon.getPolygons()) {
            polygons.add(addPolygonToMap(polygonStyle, geoJsonPolygon));
        }
        return polygons;
    }

    /**
     * Adds all GeoJsonGeometry objects stored in the GeoJsonGeometryCollection onto the map.
     * Supports recursive GeometryCollections.
     *
     * @param feature           contains relevant styling properties for the GeoJsonGeometry inside
     *                          the GeoJsonGeometryCollection
     * @param geoJsonGeometries contains an array of GeoJsonGeometry objects
     * @return array of Marker, Polyline, Polygons that have been added to the map
     */
    private ArrayList<Object> addGeometryCollectionToMap(GeoJsonFeature feature,
                                                         List<GeoJsonGeometry> geoJsonGeometries) {
        ArrayList<Object> geometries = new ArrayList<Object>();
        for (GeoJsonGeometry geometry : geoJsonGeometries) {
            geometries.add(addFeatureToMap(feature, geometry));
        }
        return geometries;
    }

    /**
     * Redraws a given GeoJsonFeature onto the map. The map object is obtained from the mFeatures
     * hashmap and it is removed and added.
     *
     * @param feature feature to redraw onto the map
     */
    private void redrawFeatureToMap(GeoJsonFeature feature) {
        redrawFeatureToMap(feature, mMap);
    }

    private void redrawFeatureToMap(GeoJsonFeature feature, AirMapView map) {
        removeFromMap(mFeatures.get(feature));
        mFeatures.put(feature, FEATURE_NOT_ON_MAP);
        mMap = map;
        if (map != null && feature.hasGeometry()) {
            mFeatures.put(feature, addFeatureToMap(feature, feature.getGeometry()));
        }
    }

    /**
     * Update is called if the developer sets a style or geometry in a GeoJsonFeature object
     *
     * @param observable GeoJsonFeature object
     * @param data       null, no extra argument is passed through the notifyObservers method
     */
    public void update(Observable observable, Object data) {
        if (observable instanceof GeoJsonFeature) {
            GeoJsonFeature feature = ((GeoJsonFeature) observable);
            boolean featureIsOnMap = mFeatures.get(feature) != FEATURE_NOT_ON_MAP;
            if (featureIsOnMap && feature.hasGeometry()) {
                // Checks if the feature has been added to the map and its geometry is not null
                // TODO: change this so that we don't add and remove
                redrawFeatureToMap(feature);
            } else if (featureIsOnMap && !feature.hasGeometry()) {
                // Checks if feature is on map and geometry is null
                removeFromMap(mFeatures.get(feature));
                mFeatures.put(feature, FEATURE_NOT_ON_MAP);
            } else if (!featureIsOnMap && feature.hasGeometry()) {
                // Checks if the feature isn't on the map and geometry is not null
                addFeature(feature);
            }
        }
    }
}
