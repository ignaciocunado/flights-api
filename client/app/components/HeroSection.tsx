'use client';

import {useMemo, useState} from "react";
import { Button } from "@/app/components/ui/button";
import { Input } from "@/app/components/ui/input";
import { Plane, ArrowLeftRight } from "lucide-react";
import {Airport} from "@/app/lib/definitions";

interface HeroSectionProps {
  airports: Airport[];
  onSearch: (params: { from: string; to: string }) => void;
}
export function HeroSection({ airports, onSearch }: HeroSectionProps) {
  const [from, setFrom] = useState('');
  const [to, setTo] = useState('');

  const handleSearch = () => {
    onSearch({ from, to });
  };

  const handleSwapLocations = () => {
    setFrom(to);
    setTo(from);
  };

  const fromSuggestions = useMemo(() => {
    const q = from.trim().toLowerCase();
    if(q === '') {
      return [];
    }
    if (!q) return airports.slice(0, 10);

    return airports
        .filter((a) => {
          const haystack = `${a.city} ${a.code} ${a.name ?? ""} ${a.country ?? ""}`.toLowerCase();
          return haystack.includes(q);
        })
        .slice(0, 10);
  }, [airports, from]);

  // suggestions for "To"
  const toSuggestions = useMemo(() => {
    const q = to.trim().toLowerCase();
    if(q === '') {
      return [];
    }
    if (!q) return airports.slice(0, 10);

    return airports
        .filter((a) => {
          const haystack = `${a.city} ${a.code} ${a.name ?? ""} ${a.country ?? ""}`.toLowerCase();
          return haystack.includes(q);
        })
        .slice(0, 10);
  }, [airports, to]);

  return (
      <div className="relative bg-booking-100 text-white">
        <div className="container mx-auto px-4 py-12">
          <div className="flex items-center justify-between mb-8">
            <div>
              <a className="flex items-center gap-2" href='/'>
                <Plane size={30} />
                <span className="text-2xl font-bold">Flights API</span>
              </a>
            </div>
          </div>

          <h1 className="text-4xl md:text-5xl font-bold mb-8 max-w-3xl">
            Search flights here!
          </h1>

          <div className="bg-white rounded-lg p-6 shadow-2xl text-gray-900">
            <div className="grid grid-cols-1 md:grid-cols-8 gap-4 mb-4">
              {/* FROM */}
              <div className="md:col-span-3 relative">
                <label className="text-sm text-gray-600 mb-1 block">From</label>
                <Input
                    value={from}
                    onChange={(e) => setFrom(e.target.value)}
                    className="text-lg font-semibold border-gray-300"
                    placeholder="City or airport"
                />
                {fromSuggestions.length > 0 && (
                    <div className="absolute z-20 mt-2 w-full rounded-lg border bg-white shadow">
                      {fromSuggestions.map((a) => {
                        const label = `${a.city} (${a.code})`;
                        return (
                            <button
                                key={a.code}
                                type="button"
                                className="w-full text-left px-3 py-2 hover:bg-gray-100"
                                onClick={() => setFrom(label)}
                            >
                              <div className="font-medium">{label}</div>
                              <div className="text-xs text-gray-500">
                                {a.country ?? ""} {a.name ? `· ${a.name}` : ""}
                              </div>
                            </button>
                        );
                      })}
                    </div>
                )}
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
              <div className="md:col-span-3 relative">
                <label className="text-sm text-gray-600 mb-1 block">To</label>
                <Input
                    value={to}
                    onChange={(e) => setTo(e.target.value)}
                    className="text-lg font-semibold border-gray-300"
                    placeholder="City or airport"
                />

                {toSuggestions.length > 0 && (
                    <div className="absolute z-20 mt-2 w-full rounded-lg border bg-white shadow">
                      {toSuggestions.map((a) => {
                        const label = `${a.city} (${a.code})`;
                        return (
                            <button
                                key={a.code}
                                type="button"
                                className="w-full text-left px-3 py-2 hover:bg-gray-100"
                                onClick={() => setTo(label)}
                            >
                              <div className="font-medium">{label}</div>
                              <div className="text-xs text-gray-500">
                                {a.country ?? ""} {a.name ? `· ${a.name}` : ""}
                              </div>
                            </button>
                        );
                      })}
                    </div>
                )}
              </div>

              {/* Search Button */}
              <div className="md:col-span-1 flex items-end">
                <Button onClick={handleSearch} className="w-full text-white font-semibold h-12 text-lg">
                  Search
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}