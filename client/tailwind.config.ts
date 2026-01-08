import type { Config } from "tailwindcss";

const config: Config = {
    darkMode: false,
    content: [
        "./app/**/*.{js,ts,jsx,tsx,mdx}",
    ],
    theme: {
        extend: {
            colors: {
                booking: "#003580",
            }
        },
    },
    plugins: [],
};

export default config;
