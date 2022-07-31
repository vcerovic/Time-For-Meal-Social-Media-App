export function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

export function replaceWithBr(text) {
    return text.replace(/\n/g, "<br />")
  }

export function removeChar(text, char){
    return text.replaceAll(char, " ")
}
  