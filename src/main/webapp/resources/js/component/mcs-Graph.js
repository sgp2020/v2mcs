/**
 ******************************************************************************
 * @file        mcs-Graph.js
 * @brief       グラフを表示する部品
 * @par
 * @author      CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief グラフコンポーネント
 * @param
 * @return
 * @retval
 * @attention
 * @note グラフコンポーネント
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsGraph = function() {
  // ------------------------------------
  // オプション
  // ------------------------------------
  this.options = {
    title: {
      display: false,
      text: ''
    },
    scales: {
      xAxes: [{
        categoryPercentage: 0.8,
        barPercentage: 1
      }],
      yAxes: [{
        ticks: {
          max: 110,
          min: 0,
          stepSize: 10
        }
      }]
    },
    elements: {
      rectangle: {
        borderWidth: 2,
        borderColor: 'rgb(128, 128, 128)',
        borderSkipped: 'bottom'
      }
    },
    responsive: false,
    legend: {
      position: 'bottom'
    }
  };
};

// ------------------------------------
// メソッド
// ------------------------------------
McsGraph.prototype = {
  /**
   ******************************************************************************
   * @brief グラフ表示処理
   * @param {Object} barChartData グラフデータ
   * @param {Object} canvasId グラフの表示先
   * @attention
   * @note グラフ生成および表示。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  show: function(barChartData, canvasId) {
    var params = {
        type: 'bar',
        data: barChartData,
        options: this.options
    };

    var ctx = document.getElementById(canvasId).getContext('2d');

    // ------------------------------------
    // 既にグラフがある場合は破棄
    // ------------------------------------
    if (this.char !== undefined) {
      this.char.destroy();
    }

    // ------------------------------------
    // canvasの幅変更
    // ------------------------------------
    var barCnt = barChartData.labels.length;
    ctx.canvas.width = barCnt * 100;

    // ------------------------------------
    // メモリの最大値と間隔を設定
    // メモリ数
    // ------------------------------------
    var stepNum = 10;

    var maxData = this._getMaxData(barChartData);
    var yAxesStep = this._calcStep(maxData, stepNum);
    var yAxesMax = yAxesStep * stepNum;

    if (maxData > 0) {
      this.options.scales.yAxes = [{
        ticks: {
          max: yAxesMax,
          min: 0,
          stepSize: yAxesStep
        }
      }];
    }

    this.char = new Chart(ctx, params);
  },

  /**
   ******************************************************************************
   * @brief グラフ最大値計算
   * @param {Object} barChartData グラフデータ
   * @return{Number} グラフデータの最大値
   * @retval
   * @attention
   * @note グラフデータの最大値を求める。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getMaxData: function(barChartData) {
    var maxData = 0;
    for (var i = 0; i < barChartData.datasets.length; i++) {
      for (var j = 0; j < barChartData.datasets[i].data.length; j++) {
        if (maxData < barChartData.datasets[i].data[j]) {
          maxData = barChartData.datasets[i].data[j];
        }
      }
    }
    return maxData;
  },

  /**
   ******************************************************************************
   * @brief グラフのメモリ計算
   * @param {Number} maxData グラフデータの最大値
   * @param {Number} stepNum メモリ数
   * @return {Number} グラフのメモリ間隔
   * @retval
   * @attention
   * @note グラフのメモリ間隔を求める
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _calcStep: function(maxData, stepNum) {
    // ------------------------------------
    // メモリ間隔の最小値を求める
    // ------------------------------------
    var minStep = Math.pow(10, maxData.toString().length - 2);
    if (minStep < 1) {
      minStep = 1;
    }
    return (Math.ceil(Math.ceil(maxData / minStep) / stepNum)) * minStep;
  },

  /**
   ******************************************************************************
   * @brief    積み上げ棒グラフ＋平均線表示処理
   * @param    {Object} options オプション類
   * @return
   * @retval
   * @attention
   * @note     引数optionsに以下の通り値を設定する
   *            {
   *              title : グラフタイトル
   *              data : グラフ設定
   *              {
   *                labels : 横軸のラベル
   *                datasets : グラフデータ
   *                [
   *                  label : 棒グラフの凡例
   *                  backgroundColor : 棒グラフの色
   *                  data : 棒グラフの値
   *                  maxDataFlags : 棒グラフの値を最大値に設定するかどうか
   *                  line : 平均線の設定
   *                  {
   *                    label : 平均線の凡例
   *                    color : 平均線の色
   *                    value : 平均線の値
   *                  }
   *                ]
   *              }
   *              ticks : 縦軸の目盛設定
   *              {
   *                max : 最大目盛
   *                min : 最小目盛
   *                stepSize : 目盛間隔
   *              }
   *              canvas : グラフを表示するキャンバス
   *              dummy : ダミーグラフを表示する際の横軸設定
   *              {
   *                baseTime : 先頭目盛の時間
   *                labelNum : 目盛数
   *                interval : 目盛の時間間隔（秒）
   *              }
   *            }
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  showWithAveLine(options) {
    var self = this;
    self.optionsWAL = options;

    // ダミーグラフ表示ならば、ダミーデータの設定を行う
    if (options.dummy) {
      self._setDummyOptions(options);
    }

    // 既にグラフがある場合は破棄
    if (self.charWAL !== undefined) {
      self.charWAL.destroy();
    }

    // キャンバス設定
    self.canvasWAL = options.canvas;
    var ctx = self.canvasWAL[0].getContext('2d');
    ctx.canvas.width = options.data.labels.length * 100;

    // 縦軸の設定
    var stepNum = 10;
    var maxData = self._getMaxData(options.data);
    var yAxesStep = self._calcStep(maxData, stepNum);
    var yAxesMax = yAxesStep * stepNum;
    if (!options.ticks || options.ticks.max < yAxesMax) {
      options.ticks = {
        max: yAxesMax,
        min: 0,
        stepSize: yAxesStep
      };
    }

    // 最大値表示をするグラフの設定
    var datasets = options.data.datasets;
    for (var i = 0; i < datasets.length; i++) {
      if (!datasets[i].data) {
        datasets[i].data = [];
      }
      if (!datasets[i].maxDataFlags) {
        datasets[i].maxDataFlags = [];
      }
      if (datasets[i].data.length === 0 && datasets[i].maxDataFlags.length > 0) {
        datasets[i].data = Array(datasets[i].maxDataFlags.length);
      }
      for (var j = 0; j < datasets[i].maxDataFlags.length; j++) {
        if (datasets[i].maxDataFlags[j]) {
          datasets[i].data[j] = options.ticks.max;
        }
      }
    }

    // グラフデータに平均線を追加
    // Step4P2 2017_11_17：プラグインを用いて平均線を描画するように修正
    var annotations = [];
    options.data.datasets = self._addAverageLine(options.data.datasets, annotations);

    // 凡例クリック時のイベント設定
    var onLegendClick = function(e, legendItem) {
      var index = legendItem.datasetIndex;
      var ci = this.chart;
      var meta = ci.getDatasetMeta(index);
      var dataset = ci.data.datasets[index];
      meta.hidden = meta.hidden === null ? !dataset.hidden : null;
      dataset.display = !dataset.display;
      ci.update();
    };

    // 平均線の描画設定
    var drawLines = {
        // Step4P2 2017_11_17：プラグインを用いて平均線を描画するように修正
        beforeDraw: function(ci) {
        var datasets = ci.data.datasets;
        for (var i = 0; i < datasets.length; i++) {
          // 平均線判定
          if (datasets[i].type == 'line') {
            // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
            ci.annotation.elements[i.toString()].options.hidden = !(datasets[i].display && datasets[datasets[i].barIndex].display && datasets[i].value);
          }
        }
      }
    };
    Chart.pluginService.register(drawLines);

    // グラフ生成時のオプション設定
    // Step4P2 2017_11_17：プラグインを用いて平均線を描画するように修正
    var opt = {
        type: 'bar',
        data: options.data,
        options: {
          title: {
            display: true,
            text: options.title
          },
          responsive: false,
          legend: {
            position: 'bottom',
            onClick: onLegendClick
          },
          scales: {
            xAxes: [{
              stacked: true
              }],
            yAxes: [{
              stacked: true,
              ticks: options.ticks
            }]
          },
          annotation: {
            annotations: annotations
          }
        }
    };
    // グラフ生成
    self.charWAL = new Chart(ctx, opt);
  },

  /**
   ******************************************************************************
   * @brief     平均線の追加
   * @param     {Array} datasets    棒グラフデータ
   * @param     {Array} annotations 平均線描画情報
   * @return    {Array} 平均線＋棒グラフデータ
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _addAverageLine(datasets, annotations) {
    // 平均線の数を求める
    var lineNum = 0;
    for (var i = 0; i < datasets.length; i++) {
      datasets[i].type = 'bar';
      datasets[i].display = true;
      datasets[i].borderWidth = 0;
      if (datasets[i].line) {
        lineNum++;
      }
    }

    // 平均線の設定
    var lineData = [];
    for (var i = 0; i < datasets.length; i++) {
      var line = datasets[i].line;
      if (line) {
        line.index = lineData.length;
        var value = line.value ? line.value : this._calcAverage(datasets[i].data);
        // 凡例表示用データ
        lineData.push({
          type: 'line',
          data: [],
          borderWidth: 3,
          barIndex: lineNum + i,
          display: true,
          value: value,
          label: line.label ? line.label : datasets[i].label + 'Average',
          borderColor: line.color,
          backgroundColor: line.color
        });
        // 線描画用データ
        // Step4P2 2017_11_17：プラグインを用いて平均線を描画するように修正
        annotations.push({
          type: 'line',
          id: line.index.toString(),
          hidden: false,
          mode: 'horizontal',
          scaleID: 'y-axis-0',
          value: value,
          borderColor: line.color,
          borderWidth: 3
        });
      }
    }
    return lineData.concat(datasets);
  },

  /**
   ******************************************************************************
   * @brief     平均線の計算
   * @param     {Array} values 平均を求める棒グラフのデータ
   * @return    {Number} 平均線の値
   * @retval
   * @attention
   * @note      nullは計算から除外する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _calcAverage(values) {
    var cnt = 0;
    var sum = 0;
    for (var i = 0; i < values.length; i++) {
      if (values[i] != null) {
        cnt++;
        sum += values[i];
      }
    }
    return cnt === 0 ? 0 : sum / cnt;
  },

  /**
   ******************************************************************************
   * @brief     ダミーグラフ設定
   * @param     {Object} options オプション類
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setDummyOptions(options) {
    // 時刻の取得
    var baseTime = options.dummy.baseTime;
    var year = baseTime.getFullYear();
    var month = ('0' + (baseTime.getMonth() + 1)).slice(-2);
    var date = ('0' + baseTime.getDate()).slice(-2);
    var hours = ('0' + baseTime.getHours()).slice(-2);
    var minutes =('0' + baseTime.getMinutes()).slice(-2);
    var seconds = ('0' + baseTime.getSeconds()).slice(-2);

    // タイトルの設定
    if (!options.title) {
      options.title = year + '/' + month + '/' + date + ' ' + hours + ':' + minutes + ':' + seconds;
    }

    // 横軸のラベルの設定
    if (!options.data.labels) {
      options.data.labels = [];
      for (var i = 0; i < options.dummy.labelNum; i++) {
        var label = (year % 100) + '/' + month + '/' + date;

        // 分までのラベル設定
        if (options.dummy.interval < 86400) {
          label += ' ' + hours + ':' + minutes;
        } else {
          label += ' 00:00';
        }

        options.data.labels.push(label);

        // 次のラベル用に値を更新
        baseTime.setSeconds(baseTime.getSeconds() + options.dummy.interval);
        month = ('0' + (baseTime.getMonth() + 1)).slice(-2);
        date = ('0' + baseTime.getDate()).slice(-2);
        hours = ('0' + baseTime.getHours()).slice(-2);
        minutes = ('0' + baseTime.getMinutes()).slice(-2);
      }
    }

    // データの設定
    for (var i = 0; i < options.data.datasets.length; i++) {
      if (!options.data.datasets[i].data) {
        options.data.datasets[i].data = [];
      }
    }
  }
};
