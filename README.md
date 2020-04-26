# WeatherApp

Android application collecting weather data from Open Weather Map API, and showing them.

You can select some sample cities or get your current GPS position and collect weather data for that place.

There collecting data service is added to Job Scheduler and the data is gathered even if application is in background on APILevel >= 26.  

All gathered data is saved in SQLite database.
