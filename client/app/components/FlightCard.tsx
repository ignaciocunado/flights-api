'use client';

import { useState } from "react";
import { Flight } from "@/app/lib/definitions";
import { Card, CardContent } from "@/app/components/ui/card";
import { Button } from "@/app/components/ui/button";
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from "@/app/components/ui/collapsible";
import {ArrowRight, ChevronDown, ChevronUp, Coffee, Pizza, Plane, Sofa, Tv, Users, Wifi} from "lucide-react";

interface FlightCardProps {
    flight: Flight;
}

const amenityIcons: Record<string, React.ReactNode> = {
    "WIFI": <div className="flex items-center gap-2"><Wifi className="h-4 w-4" /> Wi-Fi</div>,
    "ENTERTAINMENT": <div className="flex items-center gap-2"><Tv className="h-4 w-4" /> Entertainment</div>,
    "MEALS": <div className="flex items-center gap-2"><Pizza className="h-4 w-4" />Meals included</div>,
    "PREMIUM_SEATS": <div className="flex items-center gap-2"><Sofa className="h-4 w-4" />Premium Seats</div>,
};

function timeConvert(mins: number) {
    const hours = Math.floor(mins / 60);
    const minutes = mins % 60;
    return hours + "h " + minutes + "m";
}

function formatDate(iso: string, timezone: string) {
    const date = new Date(iso);

    return new Intl.DateTimeFormat("en-GB", {
        timeZone: timezone,
        dateStyle: "short",
        timeStyle: "short",
    }).format(date);
}


export function FlightCard({ flight }: FlightCardProps) {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <Card className="overflow-hidden transition-all duration-300 hover:shadow-lg border-border/50 bg-booking-100 text-white">
            <Collapsible open={isOpen} onOpenChange={setIsOpen}>
                <CollapsibleTrigger asChild>
                    <CardContent className="p-6 cursor-pointer">
                        <div className="flex flex-col lg:flex-row lg:items-center gap-6">
                            {/* Airline Info */}
                            <div className="flex items-center gap-3 lg:w-40">
                                <div>
                                    <strong><p className="font-semibold ">{flight.airline}</p></strong>
                                    <p className="text-sm text-muted-foreground">{flight.flightNumber}</p>
                                </div>
                            </div>

                            {/* Flight Route */}
                            <div className="flex-1 flex items-center gap-4">
                                <div className="text-center lg:text-left">
                                    <p className="text-2xl font-bold ">{flight.origin.code}</p>
                                    <p className="text-sm font-medium ">{formatDate(flight.departureTime, flight.origin.timezone)}</p>
                                    <p className="text-xs text-muted-foreground">{flight.origin.city}</p>
                                </div>

                                <div className="flex-1 flex items-center gap-2 px-4">
                                    <div className="flex-1 border-t-2 border-dashed border-border"></div>
                                    <div className="flex flex-col items-center">
                                        <Plane className="h-5 w-5 text-primary rotate-45" />
                                        <span className="text-xs text-muted-foreground mt-1">{timeConvert(flight.duration)}</span>
                                    </div>
                                    <div className="flex-1 border-t-2 border-dashed border-border"></div>
                                </div>

                                <div className="text-center lg:text-right">
                                    <p className="text-2xl font-bold ">{flight.destination.code}</p>
                                    <p className="text-sm font-medium ">{formatDate(flight.arrivalTime, flight.destination.timezone)}</p>
                                    <p className="text-xs text-muted-foreground">{flight.destination.city}</p>
                                </div>
                            </div>

                            {/* Price and Book */}
                            <div className="flex lg:flex-col items-center lg:items-end gap-4 lg:gap-2">
                                <div className="text-right">
                                    <p className="text-2xl font-bold text-primary">${flight.price / 100}</p>
                                    <p className="text-xs text-muted-foreground">per person</p>
                                </div>
                                <div className="flex items-center gap-2">
                                    {isOpen ? (
                                        <ChevronUp className="h-5 w-5 text-muted-foreground" />
                                    ) : (
                                        <ChevronDown className="h-5 w-5 text-muted-foreground" />
                                    )}
                                </div>
                            </div>
                        </div>
                    </CardContent>
                </CollapsibleTrigger>

                <CollapsibleContent>
                    <div className="border-t border-border bg-accent/30 px-6 py-4">
                        <div className="grid md:grid-cols-3 gap-6">
                            {/* Flight Details */}
                            <div>
                                <h3 className="font-semibold mb-3 flex items-center gap-2">
                                    <Plane className="h-4 w-4" />
                                    Flight Details
                                </h3>
                                <div className="space-y-2 text-sm">
                                    <p><span className="text-muted-foreground">From:</span> <span className="">{flight.origin.name}</span></p>
                                    <p><span className="text-muted-foreground">To:</span> <span className="">{flight.destination.name}</span></p>
                                </div>
                            </div>

                            {/* Amenities */}
                            <div>
                                <h3 className="font-semibold  mb-3 flex items-center gap-2">
                                    <Coffee className="h-4 w-4" />
                                    Amenities
                                </h3>
                                <div className="space-y-2 text-sm">
                                    {flight.amenities.map(a => amenityIcons[a])}
                                </div>
                            </div>

                            {/* Availability & Book */}
                            <div>
                                <h3 className="font-semibold  mb-3 flex items-center gap-2">
                                    <Users className="h-4 w-4" />
                                    {flight.availableSeats} seats available
                                </h3>
                                <Button className="w-5/12 bg-booking text-white font-semibold h-12 text-lg" size="lg">
                                    Book Now
                                    <ArrowRight className="ml-2 h-4 w-4" />
                                </Button>
                            </div>
                        </div>
                    </div>
                </CollapsibleContent>
            </Collapsible>
        </Card>
    );
}