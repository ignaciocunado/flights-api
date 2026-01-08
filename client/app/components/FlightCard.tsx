'use client';

import { useState } from "react";
import { Flight } from "@/app/lib/definitions";
import { Card, CardContent } from "@/app/components/ui/card";
import { Badge } from "@/app/components/ui/badge";
import { Button } from "@/app/components/ui/button";
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from "@/app/components/ui/collapsible";
import { Plane, Clock, Users, ChevronDown, ChevronUp, Wifi, Tv, Plug, Coffee, ArrowRight } from "lucide-react";

interface FlightCardProps {
    flight: Flight;
}

const amenityIcons: Record<string, React.ReactNode> = {
    "WiFi": <Wifi className="h-4 w-4" />,
    "Entertainment": <Tv className="h-4 w-4" />,
    "Power Outlets": <Plug className="h-4 w-4" />,
    "Snacks": <Coffee className="h-4 w-4" />,
    "Meals Included": <Coffee className="h-4 w-4" />,
};

export function FlightCard({ flight }: FlightCardProps) {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <Card className="overflow-hidden transition-all duration-300 hover:shadow-lg border-border/50 bg-card">
            <Collapsible open={isOpen} onOpenChange={setIsOpen}>
                <CollapsibleTrigger asChild>
                    <CardContent className="p-6 cursor-pointer">
                        <div className="flex flex-col lg:flex-row lg:items-center gap-6">
                            {/* Airline Info */}
                            <div className="flex items-center gap-3 lg:w-40">
                                <div className="text-3xl">{flight.airlineLogo}</div>
                                <div>
                                    <p className="font-semibold text-foreground">{flight.airline}</p>
                                    <p className="text-sm text-muted-foreground">{flight.flightNumber}</p>
                                </div>
                            </div>

                            {/* Flight Route */}
                            <div className="flex-1 flex items-center gap-4">
                                <div className="text-center lg:text-left">
                                    <p className="text-2xl font-bold text-foreground">{flight.departure.time}</p>
                                    <p className="text-sm font-medium text-foreground">{flight.departure.code}</p>
                                    <p className="text-xs text-muted-foreground">{flight.departure.city}</p>
                                </div>

                                <div className="flex-1 flex items-center gap-2 px-4">
                                    <div className="flex-1 border-t-2 border-dashed border-border"></div>
                                    <div className="flex flex-col items-center">
                                        <Plane className="h-5 w-5 text-primary rotate-90" />
                                        <span className="text-xs text-muted-foreground mt-1">{flight.duration}</span>
                                        {flight.stops > 0 && (
                                            <Badge variant="secondary" className="text-xs mt-1">
                                                {flight.stops} stop
                                            </Badge>
                                        )}
                                    </div>
                                    <div className="flex-1 border-t-2 border-dashed border-border"></div>
                                </div>

                                <div className="text-center lg:text-right">
                                    <p className="text-2xl font-bold text-foreground">{flight.arrival.time}</p>
                                    <p className="text-sm font-medium text-foreground">{flight.arrival.code}</p>
                                    <p className="text-xs text-muted-foreground">{flight.arrival.city}</p>
                                </div>
                            </div>

                            {/* Price and Book */}
                            <div className="flex lg:flex-col items-center lg:items-end gap-4 lg:gap-2">
                                <div className="text-right">
                                    <p className="text-2xl font-bold text-primary">${flight.price}</p>
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
                                <h4 className="font-semibold text-foreground mb-3 flex items-center gap-2">
                                    <Plane className="h-4 w-4" />
                                    Flight Details
                                </h4>
                                <div className="space-y-2 text-sm">
                                    <p><span className="text-muted-foreground">Aircraft:</span> <span className="text-foreground">{flight.aircraft}</span></p>
                                    <p><span className="text-muted-foreground">From:</span> <span className="text-foreground">{flight.departure.airport}</span></p>
                                    <p><span className="text-muted-foreground">To:</span> <span className="text-foreground">{flight.arrival.airport}</span></p>
                                    {flight.stopCity && (
                                        <p><span className="text-muted-foreground">Layover:</span> <span className="text-foreground">{flight.stopCity}</span></p>
                                    )}
                                </div>
                            </div>

                            {/* Amenities */}
                            <div>
                                <h4 className="font-semibold text-foreground mb-3 flex items-center gap-2">
                                    <Wifi className="h-4 w-4" />
                                    Amenities
                                </h4>
                                <div className="flex flex-wrap gap-2">
                                    {flight.amenities.map((amenity) => (
                                        <Badge key={amenity} variant="outline" className="flex items-center gap-1">
                                            {amenityIcons[amenity]}
                                            {amenity}
                                        </Badge>
                                    ))}
                                </div>
                            </div>

                            {/* Availability & Book */}
                            <div className="flex flex-col justify-between">
                                <div className="flex items-center gap-2 text-sm mb-4">
                                    <Users className="h-4 w-4 text-muted-foreground" />
                                    <span className={`font-medium ${flight.seatsAvailable < 10 ? 'text-destructive' : 'text-foreground'}`}>
                    {flight.seatsAvailable} seats available
                  </span>
                                </div>
                                <Button className="w-full" size="lg">
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