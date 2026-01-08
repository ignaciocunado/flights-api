import { Plane } from "lucide-react";
import { Button } from "@/components/ui/button";

export function Header() {
  return (
    <header className="bg-card border-b border-border/50 sticky top-0 z-50">
      <div className="container mx-auto px-4 h-16 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <div className="bg-primary rounded-lg p-2">
            <Plane className="h-5 w-5 text-primary-foreground" />
          </div>
          <span className="text-xl font-bold text-foreground">SkySearch</span>
        </div>
        <nav className="hidden md:flex items-center gap-6">
          <a href="#" className="text-muted-foreground hover:text-foreground transition-colors text-sm font-medium">
            Flights
          </a>
          <a href="#" className="text-muted-foreground hover:text-foreground transition-colors text-sm font-medium">
            Deals
          </a>
          <a href="#" className="text-muted-foreground hover:text-foreground transition-colors text-sm font-medium">
            My Trips
          </a>
        </nav>
        <div className="flex items-center gap-3">
          <Button variant="ghost" size="sm">
            Sign In
          </Button>
          <Button size="sm">
            Sign Up
          </Button>
        </div>
      </div>
    </header>
  );
}
