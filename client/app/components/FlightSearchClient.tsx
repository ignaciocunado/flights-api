'use client';

import { useState, useMemo } from "react";
import { HeroSection } from "./HeroSection";
import { FlightCard } from "./FlightCard";
import { Badge } from "./ui/badge";
import {Airport, Flight} from "../lib/definitions";
import { Plane } from "lucide-react"
import {Footer} from "@/app/components/Footer";

interface FlightSearchClientProps {
    flights: Flight[];
    airports: Airport[];
}

export default function FlightSearchClient({ flights, airports }: FlightSearchClientProps) {
    const [searchQuery, setSearchQuery] = useState("");
    const [appliedSearch, setAppliedSearch] = useState("");

    const handleSearch = () => {
        setAppliedSearch(searchQuery);
    };

    return (
        <div className="min-h-screen bg-background">
            <HeroSection
                airports={airports}
                onSearch={handleSearch}
            />

            <main className="container mx-auto px-4 py-8">
                {/* Results Header */}
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-6">
                    <div className="flex items-center gap-3">
                        <h2 className="text-2xl font-bold text-foreground">
                            Available flights
                        </h2>
                        <Badge variant="default" className="text-sm">
                            {flights.length} {flights.length === 1 ? 'flight' : 'flights'}
                        </Badge>
                    </div>
                </div>

                <div className="flex gap-8">
                    {/* Flight Results */}
                    <div className="flex-1">
                        {flights.length > 0 ? (
                            <div className="space-y-4">
                                {flights.map((flight) => (
                                    <FlightCard key={flight.id} flight={flight} />
                                ))}
                            </div>
                        ) : (
                            <div className="text-center py-16">
                                <div className="inline-flex items-center justify-center w-16 h-16 rounded-full bg-muted mb-4">
                                    <Plane className="h-8 w-8 text-muted-foreground" />
                                </div>
                                <h3 className="text-xl font-semibold text-foreground mb-2">
                                    No flights found
                                </h3>
                                <p className="text-muted-foreground mb-4">
                                    Try adjusting your search or filters to find more options.
                                </p>
                            </div>
                        )}
                    </div>
                </div>
            </main>

            <Footer />
        </div>
    );
}