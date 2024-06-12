let map;
let markers = [];
let sourceMarker, destMarker;
let pathPolyline;

const METRO_MANILA_BOUNDS = {
    north: 14.8855000,
    south: 14.4460529,
    west: 120.8232483,
    east: 121.2350312
};

async function initMap() {
    const { Map } = await google.maps.importLibrary("maps");

    map = new Map(document.getElementById("map"), {
        center: { lat: 14.653843959448553, lng: 121.0332868139867 }, // Set center to TriNoma
        restriction: {
            latLngBounds: METRO_MANILA_BOUNDS,
            strictBounds: false
        },
        zoom: 12,
        mapTypeId: "roadmap",
        disableDefaultUI: true,
        zoomControl: true,
        streetViewControl: false,
        mapId: "DEMO_MAP_ID"
    });
}

function getJeepneyIcon() {
    return {
        url: '../images/jeepney-pin.png',
        scaledSize: new google.maps.Size(23.5, 35)
    };
}

function addMarker(location, title) {
    const marker = new google.maps.Marker({
        position: location,
        map: map,
        title: title,
        icon: getJeepneyIcon()
    });
    markers.push(marker);
}

function createMarker(location, title) {
    return new google.maps.Marker({
        position: location,
        map: map,
        title: title,
        icon: getJeepneyIcon()
    });
}

function setLocations() {
    const source = document.getElementById("source").value;
    const destination = document.getElementById("destination").value;
    window.javaApp.setLocations(source, destination);
}

// Function to clear existing markers and polylines
function clearExistingMapElements() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
    if (pathPolyline) {
        pathPolyline.setMap(null);
        pathPolyline = null;
    }
}

// Function to add markers for the calculated path
function addPathMarkers(pathData) {
    const pathPoints = JSON.parse(pathData);
    pathPoints.forEach(point => {
        addMarker({ lat: point.lat, lng: point.lng }, point.loc);
    });
}

// Function to initialize the path polyline
function initializePathPolyline() {
    let path = new google.maps.MVCArray();
    pathPolyline = new google.maps.Polyline({
        map: map,
        strokeColor: "#FF0000",
        strokeOpacity: 0.7,
        strokeWeight: 5,
        geodesic: true
    });
    pathPolyline.setPath(path);
}

// Function to set map bounds based on path data
function setMapBounds(pathData) {
    const bounds = new google.maps.LatLngBounds();
    const pathPoints = JSON.parse(pathData);
    pathPoints.forEach(point => {
        bounds.extend(point);
    });
    map.fitBounds(bounds);
}

// Function to process each segment of the path
function processPathSegments(pathCoordinates, index, directionsService, path) {
    if (index >= pathCoordinates.length - 1) return;

    const src = pathCoordinates[index];
    const des = pathCoordinates[index + 1];

    directionsService.route({
        origin: src,
        destination: des,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    }, function(result, status) {
        if (status === google.maps.DirectionsStatus.OK) {
            result.routes[0].overview_path.forEach(point => {
                path.push(point);
            });
            processPathSegments(pathCoordinates, index + 1, directionsService, path);
        }
    });
}

// Function to handle the A* algorithm call
function drawPath(pathData) {
    clearExistingMapElements();
    addPathMarkers(pathData);
    
    const pathCoordinates = JSON.parse(pathData).map(point => ({ lat: point.lat, lng: point.lng }));

    initializePathPolyline();
    setMapBounds(pathData);

    const directionsService = new google.maps.DirectionsService();
    processPathSegments(pathCoordinates, 0, directionsService, pathPolyline.getPath());
}

document.addEventListener("DOMContentLoaded", () => {
    const apiKey = GOOGLE_MAPS_API_KEY;
    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap&v=3`;
    script.async = true;
    script.defer = true;
    document.head.appendChild(script);
});