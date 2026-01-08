// app/page.tsx
import FlightSearchClient from '@/app/components/FlightSearchClient';
import {fetchAllFlights} from "@/app/lib/api";
import {Airport, Flight} from "@/app/lib/definitions";
import {wait} from "next/dist/lib/wait";

export default async function Page() {
  // const flights = await fetchAllFlights();

    const amsterdamSchiphol: Airport = {
        id: 1,
        code: "AMS",
        name: "Amsterdam Airport Schiphol",
        city: "Amsterdam",
        country: "Netherlands",
        timezone: "Europe/Amsterdam",
    };

    const madridBarajas: Airport = {
        id: 2,
        code: "MAD",
        name: "Adolfo Suárez Madrid–Barajas Airport",
        city: "Madrid",
        country: "Spain",
        timezone: "CET",
    };

    const flight: Flight = {
        id: 101,
        flightNumber: "KL1701",
        airline: "KLM",
        origin: amsterdamSchiphol,
        destination: madridBarajas,
        departureTime: "2026-03-15T09:30:00",
        arrivalTime: "2026-03-15T12:10:00",
        duration: 160,
        price: 18999,
        currency: "EUR",
        availableSeats: 42,
    };

    return <FlightSearchClient flights={[flight]} />;
}