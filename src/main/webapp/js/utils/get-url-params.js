export default function() {
    const searchString = new URLSearchParams(window.location.search);

    const params = {};

    searchString.forEach((value, key) => {
        params[key] = value;
    });

    return params;
}