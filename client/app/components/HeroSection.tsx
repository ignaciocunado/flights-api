import { SearchBar } from "./SearchBar";

interface HeroSectionProps {
  searchQuery: string;
  onSearchChange: (value: string) => void;
  onSearch: () => void;
}

export function HeroSection({ searchQuery, onSearchChange, onSearch }: HeroSectionProps) {
  return (
    <section className="relative bg-gradient-to-br from-secondary via-secondary/90 to-primary/20 py-16 md:py-24">
      <div className="absolute inset-0 bg-[url('data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNjAiIGhlaWdodD0iNjAiIHZpZXdCb3g9IjAgMCA2MCA2MCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48ZyBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPjxnIGZpbGw9IiNmZmZmZmYiIGZpbGwtb3BhY2l0eT0iMC4wMyI+PGNpcmNsZSBjeD0iMzAiIGN5PSIzMCIgcj0iMiIvPjwvZz48L2c+PC9zdmc+')] opacity-50"></div>
      <div className="container mx-auto px-4 relative">
        <div className="max-w-3xl mx-auto text-center mb-10">
          <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold text-secondary-foreground mb-4">
            Find Your Perfect Flight
          </h1>
          <p className="text-lg md:text-xl text-secondary-foreground/80">
            Search thousands of flights and discover the best deals for your next adventure
          </p>
        </div>
        <div className="max-w-4xl mx-auto">
          <SearchBar
            searchQuery={searchQuery}
            onSearchChange={onSearchChange}
            onSearch={onSearch}
          />
        </div>
      </div>
    </section>
  );
}
