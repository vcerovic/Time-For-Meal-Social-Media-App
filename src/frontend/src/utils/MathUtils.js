export const calculateRating = (ratings) => {
    if (ratings.length === 0) {
        return 0;
    }
    let total = 0;
    ratings.forEach(rating => total += rating.rating);
    let rating = total / ratings.length;
    
    return Number.isInteger(rating) ? rating : rating.toFixed(1);
}
