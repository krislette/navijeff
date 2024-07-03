let map;
let markers = [];
let pathPolyline;

const METRO_MANILA_BOUNDS = {
    north: 14.8855000,
    south: 14.4460529,
    west: 120.8232483,
    east: 121.2350312
};

const lightModeStyles = [];

const nightModeStyles = [
    { elementType: "geometry", stylers: [{ color: "#242f3e" }] },
    { elementType: "labels.text.stroke", stylers: [{ color: "#242f3e" }] },
    { elementType: "labels.text.fill", stylers: [{ color: "#746855" }] },
    {
      featureType: "administrative.locality",
      elementType: "labels.text.fill",
      stylers: [{ color: "#d59563" }],
    },
    {
      featureType: "poi",
      elementType: "labels.text.fill",
      stylers: [{ color: "#d59563" }],
    },
    {
      featureType: "poi.park",
      elementType: "geometry",
      stylers: [{ color: "#263c3f" }],
    },
    {
      featureType: "poi.park",
      elementType: "labels.text.fill",
      stylers: [{ color: "#6b9a76" }],
    },
    {
      featureType: "road",
      elementType: "geometry",
      stylers: [{ color: "#38414e" }],
    },
    {
      featureType: "road",
      elementType: "geometry.stroke",
      stylers: [{ color: "#212a37" }],
    },
    {
      featureType: "road",
      elementType: "labels.text.fill",
      stylers: [{ color: "#9ca5b3" }],
    },
    {
      featureType: "road.highway",
      elementType: "geometry",
      stylers: [{ color: "#746855" }],
    },
    {
      featureType: "road.highway",
      elementType: "geometry.stroke",
      stylers: [{ color: "#1f2835" }],
    },
    {
      featureType: "road.highway",
      elementType: "labels.text.fill",
      stylers: [{ color: "#f3d19c" }],
    },
    {
      featureType: "transit",
      elementType: "geometry",
      stylers: [{ color: "#2f3948" }],
    },
    {
      featureType: "transit.station",
      elementType: "labels.text.fill",
      stylers: [{ color: "#d59563" }],
    },
    {
      featureType: "water",
      elementType: "geometry",
      stylers: [{ color: "#17263c" }],
    },
    {
      featureType: "water",
      elementType: "labels.text.fill",
      stylers: [{ color: "#515c6d" }],
    },
    {
      featureType: "water",
      elementType: "labels.text.stroke",
      stylers: [{ color: "#17263c" }],
    }
];

async function initMap() {
    const { Map } = await google.maps.importLibrary("maps");

    map = new Map(document.getElementById("map"), {
        // Set center to TriNoma
        center: { 
            lat: 14.653843959448553, 
            lng: 121.0332868139867 
        },
        restriction: {
            latLngBounds: METRO_MANILA_BOUNDS,
            strictBounds: false
        },
        zoom: 12,
        mapTypeId: "roadmap",
        zoomControl: false,
        streetViewControl: false,
//        mapId: "DEMO_MAP_ID",
        styles: lightModeStyles
    });
    
    const toggleButton = document.createElement("button");
    toggleButton.textContent = "Night Mode";
    toggleButton.classList.add("custom-map-control-button");
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(toggleButton);
    
    let nightMode = false;
    toggleButton.addEventListener("click", () => {
        nightMode = !nightMode;
        toggleButton.textContent = nightMode ? "Light Mode" : "Night Mode";
        map.setOptions({
            styles: nightMode ? nightModeStyles : lightModeStyles
        });
    });
}

function getJeepneyIcon() {
    return {
        url: "../images/jeepney-pin.png",
        scaledSize: new google.maps.Size(23.5, 35)
    };
}

function addMarker(location, title, clickable = false) {
    const marker = new google.maps.Marker({
        position: location,
        map: map,
        title: title,
        icon: getJeepneyIcon()
    });
    
    if (clickable) {
        google.maps.event.addListener(marker, "click", () => {
            const geocoder = new google.maps.Geocoder();
            geocoder.geocode({ "location": location }, (results, status) => {
                if (status === "OK") {
                    if (results[0]) {
                        const address = results[0].formatted_address;
                        window.javaApp.displayLocationInfo(title, address);
                    } else {
                        window.javaApp.displayLocationInfo(title, "No results found");
                    }
                } else {
                    window.javaApp.displayLocationInfo(title, "Geocoder failed due to: " + status);
                }
            });
        });
    }
    
    markers.push(marker);
}

function clearExistingMapElements() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
    if (pathPolyline) {
        pathPolyline.setMap(null);
        pathPolyline = null;
    }
}

function addPathMarkers(pathData) {
    const pathPoints = JSON.parse(pathData);
    pathPoints.forEach(point => {
        addMarker({ lat: point.lat, lng: point.lng }, point.loc);
    });
}

function initializePathPolyline() {
    const path = new google.maps.MVCArray();
    pathPolyline = new google.maps.Polyline({
        map: map,
        strokeColor: "#FF0000",
        strokeOpacity: 0.7,
        strokeWeight: 5,
        geodesic: true
    });
    pathPolyline.setPath(path);
}

function setMapBounds(pathData) {
    const bounds = new google.maps.LatLngBounds();
    const pathPoints = JSON.parse(pathData);
    pathPoints.forEach(point => {
        bounds.extend({ lat: point.lat, lng: point.lng });
    });
    map.fitBounds(bounds);
}

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

function drawPath(pathData) {
    clearExistingMapElements();
    addPathMarkers(pathData);

    const pathCoordinates = JSON.parse(pathData).map(point => ({ lat: point.lat, lng: point.lng }));

    initializePathPolyline();
    setMapBounds(pathData);

    const directionsService = new google.maps.DirectionsService();
    processPathSegments(pathCoordinates, 0, directionsService, pathPolyline.getPath());
}

function setStationPins(geopositions) {
    const nodes = geopositions.nodes;

    // Add nodes as markers
    nodes.forEach(node => {
        addMarker({ lat: node.latitude, lng: node.longitude }, node.location, true);
    });
}

document.addEventListener("DOMContentLoaded", () => {
    const apiKey = GOOGLE_MAPS_API_KEY;
    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap&v=3`;
    script.async = true;
    script.defer = true;
    document.head.appendChild(script);
});