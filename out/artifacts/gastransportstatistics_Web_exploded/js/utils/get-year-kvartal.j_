export default function(stringDate) {

    const result = {};

    result.year = moment(stringDate, 'YYYY-MM-DD').get('year'); 
    result.month = moment(stringDate, 'YYYY-MM-DD').get('month') + 1;
    if (result.month % 3 === 0)  {
        result.kvartal = Math.floor(result.month / 3);
    } else {
        result.kvartal = Math.floor(result.month / 3) + 1;
    }

    return result;
}