import requests
import json
from airports import airport_data

def duration_to_minutes(s: str) -> int:
    h, m = s.replace("h", "").replace("m", "").split()
    return int(h) * 60 + int(m)

def main():
    with open('flights.json') as f:
        data = json.load(f)

    flights = data['flights']
    for flight in flights:
        origin = airport_data.get_airport_by_iata(flight['origin'])
        destination = airport_data.get_airport_by_iata(flight['destination'])
        my_format =  {
            'flightNumber': flight['flight_number'],
            'airline': flight['airline'],
            'origin': {
                'code': flight['origin'],
                'name': origin[0]['airport'],
                'city': origin[0].get('city', ''),
                'country': origin[0]['country_code'],
                'timezone': origin[0]['time'],
            },
            'destination': {
                'code': flight['destination'],
                'name': destination[0]['airport'],
                'city': destination[0].get('city', ''),
                'country': destination[0]['country_code'],
                'timezone': destination[0]['time'],
            },
            'departureTime': '2026-01-09T'+flight['departure_time'],
            'arrivalTime': '2026-01-09T'+flight['arrival_time'],
            'duration': duration_to_minutes(flight['duration']),
            'price': round(flight['price'] * 100),
            'currency': 'EUR',
            'availableSeats': flight['available_seats'],
            'amenities': [
                a.upper() for a in flight['amenities']
            ],
        }

        res = requests.post(
            "http://localhost:8080/api/flights/",
            json=my_format,
            headers={"Content-Type": "application/json"},
            timeout=10,
        )

        print(res.text)


if __name__ == '__main__':
    main()