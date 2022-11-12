/** @type {import('tailwindcss').Config} */

module.exports = {
  content: ['./src/**/*.{html,js,jsx}'],
  theme: {
    extend: {
      colors: {
        'd-dark': '#202225',
        'd-light': '#2F3136',
        'd-lighter': '#36393F',
        'd-point': '#70A7FF',
      },
    },
  },
  plugins: [],
};
