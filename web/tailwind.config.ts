import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        background: "var(--background)",
        foreground: "var(--foreground)",
        titan: {
          dark: "#0F1115",
          surface: "#1C1B1F",
          border: "#2D2D31",
          primary: "#D0BCFF",
          accent: "#4ADE80",
          muted: "#A5AAB7",
        },
      },
    },
  },
  plugins: [],
};
export default config;
