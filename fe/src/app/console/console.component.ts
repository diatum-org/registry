import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, BaseChartDirective, Label } from 'ng2-charts';
import * as pluginAnnotations from 'chartjs-plugin-annotation';

import { ConsoleService } from '../service/console.service';

@Component({
  selector: 'app-console',
  templateUrl: './console.component.html',
  styleUrls: ['./console.component.scss']
})
export class ConsoleComponent implements OnInit {

  public lineChartData: ChartDataSets[] = [
    { data: [] }
  ];
  public lineChartLabels: Label[] = [];

  public mode: string = "processor";
  public storageOptions: ChartOptions = { elements: { line: { borderWidth: 1 }, point: { radius: 0 } }, scales: { xAxes: [{ gridLines: { display: false } }], yAxes: [{ ticks: { min: 0, stepSize: 1024 }}] }};
  public memoryOptions: ChartOptions = { elements: { line: { borderWidth: 1 }, point: { radius: 0 } }, scales: { xAxes: [{ gridLines: { display: false } }], yAxes: [{ ticks: { min: 0, stepSize: 64 }}]}};
  public processorOptions: ChartOptions = { elements: { line: { borderWidth: 1 }, point: { radius: 0 } }, scales: { xAxes: [{ gridLines: { display: false } }], yAxes: [{ ticks: { max: 100, min: 0, stepSize: 10 } }] } };
  public requestsOptions: ChartOptions = { elements: { line: { borderWidth: 1 }, point: { radius: 0 } }, scales: { xAxes: [{ gridLines: { display: false } }], yAxes: [{ type: "logarithmic", ticks: { min: 0, stepSize: 10 } }] } };
  public accountsOptions: ChartOptions = { elements: { line: { borderWidth: 1 }, point: { radius: 0 } }, scales: { xAxes: [{ gridLines: { display: false } }], yAxes: [{ ticks: { min: 0, stepSize: 128 }}]}};

  public lineChartColors: Color[] = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartOptions = { };
  public lineChartLegend = false;
  public lineChartType = 'line';
  public lineChartPlugins = [pluginAnnotations];

  public loginToken: string = '';
  private token: string = null;
  private processor: number[] = [];
  private storage: number[] = [];
  private memory: number[] = [];
  private requests: number[] = [];
  private accounts: number[] = [];
  private times: number[] = [];

  constructor(
      private router: Router,
      private consoleService: ConsoleService
      ) {
  }

  ngOnInit() {
  }

  onKey(event) {
    if(event.key === "Enter") {
      this.onLogin();
    }
  }

  onLogin() {
    this.consoleService.getStatus(this.loginToken).then(() => {
      this.token = this.loginToken;
      this.consoleService.getStats(this.token).then(s => {
        this.processor = [];
        this.storage = [];
        this.memory = [];
        this.lineChartLabels = [];
        for(let i = 0; i < s.length; i++) {
          this.processor.unshift(s[i].processor);
          this.storage.unshift(Math.floor(s[i].storage / 1024));
          this.memory.unshift(Math.floor(s[i].memory / 1024));
          this.requests.unshift(s[i].requests);
          this.accounts.unshift(s[i].accounts);
          this.lineChartLabels.unshift('');
        }
        if(this.mode == "processor") {
          this.lineChartData = [ { data: this.processor } ];
          this.lineChartOptions = this.processorOptions;
        }
        if(this.mode == "storage") {
          this.lineChartData = [ { data: this.storage } ];
          this.lineChartOptions = this.storageOptions;
        }
        if(this.mode == "requests") {
          this.lineChartData = [ { data: this.requests } ];
          this.lineChartOptions = this.requestsOptions;
        }
        if(this.mode == "accounts") {
          this.lineChartData = [ { data: this.accounts } ];
          this.lineChartOptions = this.accountsOptions;
        }
        if(this.mode == "memory") {
          this.lineChartData = [ { data: this.memory } ];
          this.lineChartOptions = this.memoryOptions;
        }
      }).catch(err => {
        window.alert("failed to get stats");
      });
    }).catch(err => {
      console.log(err);
      window.alert("Access Denied");
    });
  }

  showLogin(): boolean {
    return this.token == null;
  }

  onMode(m: string) {
    this.mode = m;
    if(this.mode == "processor") {
      this.lineChartData = [ { data: this.processor } ];
      this.lineChartOptions = this.processorOptions;
    }
    if(this.mode == "storage") {
      this.lineChartData = [ { data: this.storage } ];
      this.lineChartOptions = this.storageOptions;
    }
    if(this.mode == "memory") {
      this.lineChartData = [ { data: this.memory } ];
      this.lineChartOptions = this.memoryOptions;
    }
    if(this.mode == "accounts") {
      this.lineChartData = [ { data: this.accounts } ];
      this.lineChartOptions = this.accountsOptions;
    }
    if(this.mode == "requests") {
      this.lineChartData = [ { data: this.requests } ];
      this.lineChartOptions = this.requestsOptions;
    }
  }

}
