module.exports = {
  extends: ["airbnb", "prettier"],
  rules: {
    quotes: ["error", "double", { avoidEscape: false }],
    indent: ["error", 2],
    "react/prop-types": "off",
  },
};
