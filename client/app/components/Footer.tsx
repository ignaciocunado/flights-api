import {Plane} from "lucide-react";

export function Footer() {
    return (
        <footer className="bg-card border-t border-border mt-16">
            <div className="container mx-auto px-4 py-8">
                <div className="flex flex-col md:flex-row items-center justify-between gap-4">
                    <div className="flex items-center gap-2">
                        <div className="bg-primary rounded-lg p-1.5">
                            <Plane className="h-4 w-4 text-primary-foreground" />
                        </div>
                        <span className="font-semibold text-foreground">SkySearch</span>
                    </div>
                    <p className="text-sm text-muted-foreground">
                        © 2026 Flights. All rights reserved.
                    </p>
                </div>
            </div>
        </footer>
    )
}


