export type Airport = {
    id: number;
    code: string
    name: string;
    city: string;
    country: string;
    timezone: string;
}

export type Flight = {
    id: number;
    flightNumber: string;
    airline: string;
    origin: Airport;
    destination: Airport;
    departureTime: string;
    arrivalTime: string;
    duration: number;
    price: number;
    currency: string;
    availableSeats: number;
}