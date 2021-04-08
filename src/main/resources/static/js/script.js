function updateEpisodes(category, episodesId) {
    if (category === 'Movie') {
        document.getElementById(episodesId).value = "1";
        document.getElementById(episodesId).setAttribute('readonly', 'readonly');
    } else {
        document.getElementById(episodesId).value = "";
        document.getElementById(episodesId).removeAttribute('readonly');
    }
}