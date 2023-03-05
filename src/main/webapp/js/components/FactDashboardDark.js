export default class FactDashboardDark {

    constructor({
        canvasName = 'canvasName',
        consumptionGas = [0, 0, 0, 0],
        millageGas = [0, 0, 0, 0],
        consumptionDt = [0, 0, 0, 0],
        millageDT = [0, 0, 0, 0],
        labels = []
    } = {}
    ){
        this.canvasName = canvasName;
        this.consumptionGas = consumptionGas;
        this.millageGas = millageGas;
        this.consumptionDt = consumptionDt;
        this.millageDT = millageDT;
        this.labels = labels;

        this.styleBlue = {
            fillColor: "rgba(0, 204, 255, 0.40)",
            borderColor: "rgb(0, 161, 236)",
            pointBackgroundColor: "rgb(0, 161, 236)"
        };

        this.styleGreen = {
            fillColor: "rgba(0, 255, 204, 0.40)",
            borderColor: "#00d6b4",
            pointBackgroundColor: "#00d6b4"
        };

        this.styleYellow = {
            fillColor: "rgba(217, 255, 0, 0.40)",
            borderColor: "#8ba300",
            pointBackgroundColor: "#8ba300"
        };

        this.styleRed = {
            fillColor: "rgba(255, 115, 0, 0.40)",
            borderColor: "#993b00",
            pointBackgroundColor: "#993b00"
        };

        this.render();
    }

    setDashParams() {
        // General configuration for the charts with Line gradientStroke
        this.dashParams = {
            maintainAspectRatio: false,
            legend: {
                display: false
            },
            tooltips: {
                bodySpacing: 4,
                mode: "nearest",
                intersect: 0,
                position: "nearest",
                xPadding: 10,
                yPadding: 10,
                caretPadding: 10
            },
            responsive: true,
            scales: {
                yAxes: [{
                    display: 0,
                    gridLines: 0,
                    ticks: {
                        display: false
                    },
                    gridLines: {
                        zeroLineColor: "transparent",
                        drawTicks: false,
                        display: false,
                        drawBorder: false
                    }
                }],
                xAxes: [{
                    display: 0,
                    gridLines: 0,
                    ticks: {
                        display: false
                    },
                    gridLines: {
                        zeroLineColor: "transparent",
                        drawTicks: false,
                        display: false,
                        drawBorder: false
                    }
                }]
            },
            layout: {
                padding: {
                    left: 0,
                    right: 0,
                    top: 15,
                    bottom: 15
                }
            }
        };
    }

    getTemplate() {
        return `<canvas id="${this.canvasName}"
                class="chartjs-render-monitor">
                </canvas>`; 
    }

    render() {

        const element = document.querySelector(`[data-element="${this.canvasName}"]`);
        element.innerHTML = this.getTemplate();
        this.element = element.firstElementChild;
        
        const ctx = this.element.getContext("2d");

        const gradientFillBlue = ctx.createLinearGradient(0, 170, 0, 50);
        gradientFillBlue.addColorStop(0, "rgba(128, 182, 244, 0)");
        gradientFillBlue.addColorStop(1, this.styleBlue.fillColor);

        const gradientFillGreen = ctx.createLinearGradient(0, 170, 0, 50);
        gradientFillGreen.addColorStop(0, "rgba(128, 244, 182, 0)");
        gradientFillGreen.addColorStop(1, this.styleGreen.fillColor);

        const gradientFillYellow = ctx.createLinearGradient(0, 170, 0, 50);
        gradientFillYellow.addColorStop(0, "rgba(230, 255, 0, 0)");
        gradientFillYellow.addColorStop(1, this.styleYellow.fillColor);

        const gradientFillRed = ctx.createLinearGradient(0, 170, 0, 50);
        gradientFillRed.addColorStop(0, "rgba(255, 77, 0, 0)");
        gradientFillRed.addColorStop(1, this.styleRed.fillColor);

        this.dash = new Chart(ctx, {
            type: 'line',
            responsive: true,
            animating: true,
            data: {
                labels: this.labels,
                datasets: [
                    {
                        label: "Потребление, газ",
                        borderColor: this.styleBlue.borderColor,
                        pointBorderColor: "#FFF",
                        pointBackgroundColor: this.styleBlue.pointBackgroundColor,
                        pointBorderWidth: 2,
                        pointHoverRadius: 4,
                        pointHoverBorderWidth: 1,
                        pointRadius: 4,
                        fill: true,
                        backgroundColor: gradientFillBlue,
                        borderWidth: 2,
                        data: this.consumptionGas
                    },
                    {
                        label: "Пробег, газ",
                        borderColor: this.styleGreen.borderColor,
                        pointBorderColor: "#FFF",
                        pointBackgroundColor: this.styleGreen.pointBackgroundColor,
                        pointBorderWidth: 2,
                        pointHoverRadius: 4,
                        pointHoverBorderWidth: 1,
                        pointRadius: 4,
                        fill: true,
                        backgroundColor: gradientFillGreen,
                        borderWidth: 2,
                        data: this.millageGas
                    },
                    {
                        label: "Потребление, ДТ",
                        borderColor: this.styleYellow.borderColor,
                        pointBorderColor: "#FFF",
                        pointBackgroundColor: this.styleYellow.pointBackgroundColor,
                        pointBorderWidth: 2,
                        pointHoverRadius: 4,
                        pointHoverBorderWidth: 1,
                        pointRadius: 4,
                        fill: true,
                        backgroundColor: gradientFillYellow,
                        borderWidth: 2,
                        data: this.consumptionDt
                    },
                    {
                        label: "Пробег, ДТ",
                        borderColor: this.styleRed.borderColor,
                        pointBorderColor: "#FFF",
                        pointBackgroundColor: this.styleRed.pointBackgroundColor,
                        pointBorderWidth: 2,
                        pointHoverRadius: 4,
                        pointHoverBorderWidth: 1,
                        pointRadius: 4,
                        fill: true,
                        backgroundColor: gradientFillRed,
                        borderWidth: 2,
                        data: this.millageDT
                    },
                ]
            },
            options: this.dashParams
        });
    }

    remove() {
        this.element.remove();
    }

    destroy() {
        this.remove();
    }


}