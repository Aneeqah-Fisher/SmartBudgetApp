package com.example.smartbudget

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val chart = findViewById<LineChart>(R.id.lineChart)

        val prefs = getSharedPreferences("user_data", MODE_PRIVATE)
        val currentUser = prefs.getString("loggedInUser", "") ?: ""

        val minGoal = prefs.getFloat("minGoal", 0f)
        val maxGoal = prefs.getFloat("maxGoal", 0f)

        Toast.makeText(
            this,
            "Min: R$minGoal  Max: R$maxGoal",
            Toast.LENGTH_LONG
        ).show()

        val db = AppDatabase.getDatabase(this)

        val categoryTotals =
            db.expenseDao().getCategoryTotals(currentUser)

        val expenseEntries = ArrayList<Entry>()
        val minGoalEntries = ArrayList<Entry>()
        val maxGoalEntries = ArrayList<Entry>()

        val categoryNames = ArrayList<String>()

        categoryTotals.forEachIndexed { index, item ->

            categoryNames.add(item.category)

            expenseEntries.add(
                Entry(
                    index.toFloat(),
                    item.total.toFloat()
                )
            )

            minGoalEntries.add(
                Entry(
                    index.toFloat(),
                    minGoal
                )
            )

            maxGoalEntries.add(
                Entry(
                    index.toFloat(),
                    maxGoal
                )
            )
        }

        // Category Spending
        val expenseSet =
            LineDataSet(expenseEntries, "Category Spending")

        expenseSet.color = Color.BLUE
        expenseSet.valueTextColor = Color.BLUE
        expenseSet.lineWidth = 3f
        expenseSet.setDrawCircles(true)
        expenseSet.setDrawValues(false)

        // Minimum Goal
        val minGoalSet =
            LineDataSet(minGoalEntries, "Minimum Goal")

        minGoalSet.color = Color.GREEN
        minGoalSet.lineWidth = 4f
        minGoalSet.setDrawCircles(false)
        minGoalSet.enableDashedLine(10f, 10f, 0f)
        minGoalSet.setDrawValues(false)

        // Maximum Goal
        val maxGoalSet =
            LineDataSet(maxGoalEntries, "Maximum Goal")

        maxGoalSet.color = Color.RED
        maxGoalSet.lineWidth = 6f
        maxGoalSet.setDrawCircles(false)
        maxGoalSet.enableDashedLine(20f, 10f, 0f)
        maxGoalSet.setDrawValues(false)

        val lineData =
            LineData(expenseSet, minGoalSet, maxGoalSet)

        chart.data = lineData

        chart.xAxis.valueFormatter =
            IndexAxisValueFormatter(categoryNames)

        chart.xAxis.position =
            XAxis.XAxisPosition.BOTTOM

        chart.xAxis.granularity = 1f
        chart.xAxis.labelRotationAngle = -45f

        chart.axisRight.isEnabled = false

        chart.description.text =
            "Category Spending vs Goals"

        val legend = chart.legend

        legend.isEnabled = true
        legend.isWordWrapEnabled = true

        legend.orientation =
            com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL

        legend.verticalAlignment =
            com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM

        legend.horizontalAlignment =
            com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.LEFT

        legend.setDrawInside(false)

        chart.setExtraBottomOffset(80f)

        chart.animateX(1500)
        chart.invalidate()
    }
}