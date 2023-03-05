export default function(year, kvartal) {

    const result = {};

    switch (kvartal)  {
        case 1: result.start = ('0000' + year).slice(-4) + '-01-01';
                result.end = ('0000' + year).slice(-4) + '-03-31';
                break;
        case 2: result.start = ('0000' + year).slice(-4) + '-04-01';
                result.end = ('0000' + year).slice(-4) + '-06-30';
                break;
        case 3: result.start = ('0000' + year).slice(-4) + '-07-01';
                result.end = ('0000' + year).slice(-4) + '-09-30';
                break;
        case 4: result.start = ('0000' + year).slice(-4) + '-10-01';
                result.end = ('0000' + year).slice(-4) + '-12-31';
                break;
    }

    return result;
}