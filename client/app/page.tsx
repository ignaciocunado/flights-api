import FlightSearchClient from '@/app/components/FlightSearchClient';
import { fetchAllFlights, searchAirports } from "@/app/lib/api";

export default async function Page() {
    const flights = await fetchAllFlights();
    const airports = await searchAirports('');

    return <FlightSearchClient flights={flights} airports={airports} />;
}