import { Flight, Airport } from './definitions';

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export async function fetchAllFlights(): Promise<Flight[]> {
    try {
        const res = await fetch(`${API_URL}/api/flights/`, {
            cache: 'no-store',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if(res.status == 404) {
            return [];
        }
        if (!res.ok) {
            throw new Error(`Failed to fetch flights: ${res.status} ${res.statusText}`);
        }

        return res.json();
    } catch (error) {
        console.error('Error fetching flights:', error);
        throw error;
    }
}

export async function searchFlights(origin?: string, destination?: string): Promise<Flight[]> {
    try {
        const queryParams = new URLSearchParams();
        if (origin) queryParams.append('origin', origin);
        if (destination) queryParams.append('destination', destination);

        const res = await fetch(`${API_URL}/api/flights?${queryParams}`, {
            cache: 'no-store',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if(res.status == 404) {
            return [];
        }
        if (!res.ok) {
            throw new Error(`Failed to search flights: ${res.status} ${res.statusText}`);
        }

        return res.json();
    } catch (error) {
        console.error('Error searching flights:', error);
        throw error;
    }
}

export async function bookFlight(id: number, version: number): Promise<Flight[]> {
    try {
        const res = await fetch(`${API_URL}/api/flights/${id}/book`, {
            method: 'PATCH',
            cache: 'no-store',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                'version': version,
            }),
        });

        if (! res.ok) {
            throw new Error(`Failed to book flight: ${res.status} ${res.statusText}`);
        }

        return res.json();
    } catch (error) {
        console.error('Error booking flight with id: ' + id, error);
        throw error;
    }
}

export async function searchAirports(query: string): Promise<Airport[]> {
    try {
        const queryParams = new URLSearchParams();
        queryParams.append('search', query);

        const res = await fetch(`${API_URL}/api/airports?${queryParams}`, {
            cache: 'no-store',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!res.ok) {
            throw new Error(`Failed to fetch airports: ${res.status} ${res.statusText}`);
        }

        return res.json();
    } catch (error) {
        console.error('Error fetching airports:', error);
        throw error;
    }
}

/**
 * Generic API helper for other endpoints
 */
export async function apiCall<T>(
    endpoint: string,
    options?: RequestInit
): Promise<T> {
    try {
        const res = await fetch(`${API_URL}${endpoint}`, {
            ...options,
            headers: {
                'Content-Type': 'application/json',
                ...options?.headers,
            },
        });

        if (!res.ok) {
            throw new Error(`API call failed: ${res.status} ${res.statusText}`);
        }

        return res.json();
    } catch (error) {
        console.error(`Error calling ${endpoint}:`, error);
        throw error;
    }
}