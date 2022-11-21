module.exports = {
  extends: ["react-app", "prettier"],
  rules: {
    quotes: ["error", "double", { avoidEscape: false }],
    indent: ["error", 2],
  },
};