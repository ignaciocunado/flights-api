import FlightSearchClient from '@/app/components/FlightSearchClient';
import {fetchAllFlights} from "@/app/lib/api";

export default async function Page() {
    const flights = await fetchAllFlights();

    return <FlightSearchClient flights={flights} />;
}