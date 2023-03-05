export default class PlanFactDashboardDark {

    constructor({
        canvasName = 'canvasName',
        dataPlan = [0, 0, 0, 0],
        dataFact = [0, 0, 0, 0]
    } = {}
    ){
        this.canvasName = canvasName;
        this.dataPlan = dataPlan;
        this.dataFact = dataFact;

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

        this.dash = new Chart(ctx, {
            type: 'line',
            responsive: true,
            animating: true,
            data: {
                labels: ["1кв", "2кв", "3кв", "4кв"],
                datasets: [
                    {
                        label: "План",
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
                        data: this.dataPlan
                    },
                    {
                        label: "Факт",
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
                        data: this.dataFact
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