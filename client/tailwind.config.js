/** @type {import('tailwindcss').Config} */

module.exports = {
  content: ["./src/**/*.{html,js,jsx}"],
  theme: {
    screens: {
      "xs": "480px"
    },
    extend: {
      colors: {
        "d-dark": "#202225",
        "d-light": "#2F3136",
        "d-lighter": "#36393F",
        "d-hover" : "#3C3F44",
        "d-select" : "#43464D",        
        "d-point": "#70A7FF",
        "d-button": "#5865F2",
        "d-button-hover": "#4752C4",
      },
    },
  },
  plugins: [],
};
