import { Search, MapPin, Calendar } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

interface SearchBarProps {
  searchQuery: string;
  onSearchChange: (value: string) => void;
  onSearch: () => void;
}

export function SearchBar({ searchQuery, onSearchChange, onSearch }: SearchBarProps) {
  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      onSearch();
    }
  };

  return (
    <div className="bg-card rounded-xl shadow-lg p-4 md:p-6 border border-border/50">
      <div className="flex flex-col md:flex-row gap-4">
        <div className="flex-1 relative">
          <MapPin className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-muted-foreground" />
          <Input
            placeholder="Search by city, airport, or airline..."
            value={searchQuery}
            onChange={(e) => onSearchChange(e.target.value)}
            onKeyDown={handleKeyDown}
            className="pl-10 h-12 text-base bg-background"
          />
        </div>
        <Button onClick={onSearch} size="lg" className="h-12 px-8">
          <Search className="h-5 w-5 mr-2" />
          Search Flights
        </Button>
      </div>
    </div>
  );
}
