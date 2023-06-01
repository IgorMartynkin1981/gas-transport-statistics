import inDifferentKvartals from './inDifferentKvartals.js';

export default function( year = moment().year() ) {

    const optWeek = [
    ];

    const weekInYear = moment().isoWeekYear(year).weeksInYear();
    for (let weekNum = 0; weekNum <= weekInYear; weekNum++) {
        /*const start = moment().year(year).isoWeek(weekNum).add(-1, 'day'); //.add(-1, 'day') исправляем то, что неделя начинается со вторника
        let stop = moment().year(year).isoWeek(weekNum).add(5, 'day');*/
        const dayOfWeek = moment().day() - 1;
        const start = moment().isoWeekYear(year).isoWeek(weekNum).add(0-dayOfWeek, 'day');;
        let stop = moment().isoWeekYear(year).isoWeek(weekNum).add(6-dayOfWeek, 'day');

        const secondDate = {};
        if ( inDifferentKvartals(start, stop) ) {

            secondDate.stop = new moment(stop);
            stop = new moment(start);
            stop.endOf('month');
            secondDate.start = new moment(start);
            secondDate.start.endOf('month').add(1, 'day');

            optWeek.push({
                id: optWeek.length + '_' + year,
                title: start.format('DD MMMM YYYY') + ' - ' + stop.format('DD MMMM YYYY'),
                startDate: start,
                stopDate: stop
            },
            {
                id: (optWeek.length+1) + '_' + year,
                title: secondDate.start.format('DD MMMM YYYY') + ' - ' + secondDate.stop.format('DD MMMM YYYY'),
                startDate: secondDate.start,
                stopDate: secondDate.stop
            });            
        } else {
            optWeek.push({
                id: optWeek.length + '_' + year,
                title: start.format('DD MMMM YYYY') + ' - ' + stop.format('DD MMMM YYYY'),
                startDate: start,
                stopDate: stop
            });
        }

    }

    return optWeek;
}