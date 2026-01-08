import { Flight } from './definitions';

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export async function fetchAllFlights(): Promise<Flight[]> {
    try {
        const res = await fetch(`${API_URL}/api/flights`, {
            cache: 'no-store',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!res.ok) {
            throw new Error(`Failed to fetch flights: ${res.status} ${res.statusText}`);
        }

        return res.json();
    } catch (error) {
        console.error('Error fetching flights:', error);
        throw error;
    }
}

export async function fetchFlightById(id: string): Promise<Flight | null> {
    try {
        const res = await fetch(`${API_URL}/api/flights/${id}`, {
            cache: 'no-store',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!res.ok) {
            if (res.status === 404) return null;
            throw new Error(`Failed to fetch flight: ${res.status} ${res.statusText}`);
        }

        return res.json();
    } catch (error) {
        console.error('Error fetching flight:', error);
        throw error;
    }
}

export async function searchFlights(params: {
    origin?: string;
    destination?: string;
    date?: string;
}): Promise<Flight[]> {
    try {
        const queryParams = new URLSearchParams();
        if (params.origin) queryParams.append('origin', params.origin);
        if (params.destination) queryParams.append('destination', params.destination);
        if (params.date) queryParams.append('date', params.date);

        const res = await fetch(`${API_URL}/api/flights/search?${queryParams}`, {
            cache: 'no-store',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!res.ok) {
            throw new Error(`Failed to search flights: ${res.status} ${res.statusText}`);
        }

        return res.json();
    } catch (error) {
        console.error('Error searching flights:', error);
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