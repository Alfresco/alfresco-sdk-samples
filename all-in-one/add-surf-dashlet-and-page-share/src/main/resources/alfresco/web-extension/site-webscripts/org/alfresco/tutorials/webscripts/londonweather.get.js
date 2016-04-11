// Most services will require an API Key
// You can get one for the openweathermap API here: http://openweathermap.org/appid
var apiKey = "3877e1ef1f9fce84b1371703196badce";
// Get weather for London in JSON structure
var londonWeatherURL = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=" + apiKey;
var connector = remote.connect("http");
var JSONString = connector.get(londonWeatherURL);

// create json object from data
// Don't use this: var londonWeatherJSON = eval('(' + JSONString + ')');, eval is not secure
var londonWeatherJSON = jsonUtils.toObject(JSONString);
model.weather = londonWeatherJSON["weather"];


/* A call such as:

    http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=3877e1ef1f9fce84b1371703196badce

   returns JSON looking something like this:

 {
   coord: {
     lon: -0.13,
     lat: 51.51
   },
   weather: [
     {
       id: 500,
       main: "Rain",
       description: "light rain",
       icon: "10d"
     }
   ],
   base: "cmc stations",
   main: {
     temp: 284.57,
     pressure: 1017,
     humidity: 81,
     temp_min: 283.95,
     temp_max: 285.15
   },
   wind: {
     speed: 8.7,
     deg: 220
   },
   clouds: {
     all: 90
   },
   dt: 1454080927,
   sys: {
     type: 1,
     id: 5093,
     message: 0.0061,
     country: "GB",
     sunrise: 1454053395,
     sunset: 1454085882
   },
   id: 2643743,
   name: "London",
   cod: 200
}

 */