import requests
import json

def duration_to_minutes(s: str) -> int:
    h, m = s.replace("h", "").replace("m", "").split()
    return int(h) * 60 + int(m)

def main():
    with open('flights.json') as f:
        data = json.load(f)

    flights = data['flights']
    for flight in flights:
        my_format =  {
            'flightNumber': flight['flight_number'],
            'airline': flight['airline'],
            'origin': {
                'code': flight['origin'],
            },
            'destination': {
                'code': flight['destination']
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