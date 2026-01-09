'use client';

import { useState } from "react";
import { Button } from "@/app/components/ui/button";
import { Input } from "@/app/components/ui/input";
import { Plane, ArrowLeftRight } from "lucide-react";

interface HeroSectionProps {
  searchQuery: string;
  onSearchChange: (query: string) => void;
  onSearch: () => void;
}

export function HeroSection({ searchQuery, onSearchChange, onSearch }: HeroSectionProps) {
  const [activeTab, setActiveTab] = useState<'flights' | 'hotels' | 'cars'>('flights');
  const [tripType, setTripType] = useState('return');
  const [from, setFrom] = useState('Madrid (MAD)');
  const [to, setTo] = useState('');
  const [departDate, setDepartDate] = useState('');
  const [returnDate, setReturnDate] = useState('');

  const handleSearch = () => {
    onSearch();
  };

  const handleSwapLocations = () => {
    const temp = from;
    setFrom(to);
    setTo(temp);
  };

  return (
      <div className="relative bg-booking-100 text-white">
        <div className="container mx-auto px-4 py-12">
          <div className="flex items-center justify-between mb-8">
            <div className="flex items-center gap-2">
              <div className="flex items-center gap-2">
                <Plane size={30} />
                <span className="text-2xl font-bold">Flights API</span>
              </div>
            </div>
          </div>

          <h1 className="text-4xl md:text-5xl font-bold mb-8 max-w-3xl">
            Search flights here!
          </h1>

          <div className="bg-white rounded-lg p-6 shadow-2xl">
            <div className="grid grid-cols-1 md:grid-cols-8 gap-4 mb-4">

              {/* FROM */}
              <div className="md:col-span-3 relative">
                <label className="text-sm text-gray-600 mb-1 block">From</label>
                <Input
                    value={from}
                    onChange={(e) => setFrom(e.target.value)}
                    className="text-lg font-semibold text-gray-900 border-gray-300"
                    placeholder="Country, city or airport"
                />
              </div>

              {/* SWAP */}
              <div className="md:col-span-1 flex items-end justify-center pb-2">
                <Button
                    variant="ghost"
                    size="icon"
                    onClick={handleSwapLocations}
                    className="rounded-full bg-gray-100 hover:bg-gray-200 text-gray-700"
                >
                  <ArrowLeftRight className="w-5 h-5" />
                </Button>
              </div>

              {/* TO */}
              <div className="md:col-span-3">
                <label className="text-sm text-gray-600 mb-1 block">To</label>
                <Input
                    value={to}
                    onChange={(e) => setTo(e.target.value)}
                    className="text-lg text-gray-900 border-gray-300"
                    placeholder="Country, city or airport"
                />
              </div>

              {/* Search Button */}
              <div className="md:col-span-1 flex items-end">
                <Button
                    onClick={handleSearch}
                    className="w-full text-white font-semibold h-12 text-lg"
                >
                  Search
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}