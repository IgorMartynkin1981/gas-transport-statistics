import inDifferentKvartals from '../utils/inDifferentKvartals.js';
import getWeeksArray from './get-weeks-array.js';

export default function(stringDate) {

    const result = {};

    const date = moment(stringDate, 'YYYY-MM-DD');
    /*
    result.numWeek = date.isoWeek();
    
    result.start =  moment().isoWeek(result.numWeek).add(-1, 'day');  //.add(-1, 'day') исправляем то, что неделя начинается с вт
    result.stop = moment().isoWeek(result.numWeek).add(5, 'day');

    if ( inDifferentKvartals(result.start, result.stop) ) {

        if ( date.month() === result.start.month() ) {
            result.stop = date.endOf('month');
        } else {
            result.start = date.startOf('month');
        }
    }
    */
    const weeks = getWeeksArray( date.year() );

    console.log(weeks);

    const wk = Object.values( weeks ).find( item => {

        item.startDate.set({'hour': 0, 'minute': 0, 'seconds': 0, 'millisecond': 0});
        item.stopDate.set({'hour': 0, 'minute': 0, 'seconds': 0, 'millisecond': 0});

        if (item.startDate.valueOf() <= date.valueOf() && item.stopDate.valueOf() >= date.valueOf()) return true;
    });

    console.log(wk);

    result.numWeek = wk.id;
    result.start = wk.startDate;
    result.stop = wk.stopDate;

    return result;
}