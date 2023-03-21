package com.example.table

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.table.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val (rowCount, columnCount) = Pair(8, 11)
        val amountOfCellsToFill = 6
        var value = 0
        var counterOfFilledCells = 0
        var counterOfFilledMembers = 0
        var sum = -1
        val arraySums = IntArray(7)
        lifecycleScope.launch {
            while (true) {
                for (i in 1 until rowCount) {
                    val row = binding.table.getChildAt(i) as? TableRow
                    row?.also {
                        for (j in 2 until columnCount - 2) {
                            if (i != j - 1) {
                                val cellWithPoints = row.getChildAt(j) as EditText
                                if (cellWithPoints.text.toString() != "") {
                                    value = cellWithPoints.text.toString().toInt()
                                    if (value in 0..5) {
                                        counterOfFilledCells++
                                        sum += value
                                    }
                                }
                            }
                        }
                        val tvSumOfPoints = (row.getChildAt(9) as? TextView)
                        if (amountOfCellsToFill == counterOfFilledCells) {
                            counterOfFilledMembers++
                            arraySums[i - 1] = sum
                            tvSumOfPoints?.text = sum.toString()
                        } else {
                            arraySums[i - 1] = -1
                            tvSumOfPoints?.text = ""
                        }

                    }
                    counterOfFilledCells = 0
                    sum = 0
                }
                arraySums.sortDescending()
                for (i in 1 until rowCount) {
                    val row = binding.table.getChildAt(i) as? TableRow
                    val tvPlaceOfMember = (row?.getChildAt(10) as? TextView)
                    val sumOfPointsOfMember = (row?.getChildAt(9) as? TextView)?.text.toString().let {
                        if(it!="")
                            it.toInt()
                        else
                            0
                    }
                    if (counterOfFilledMembers==7) {
                        val place = arraySums.indexOf(sumOfPointsOfMember) + 1
                        tvPlaceOfMember?.text = place.toString()
                    } else {
                        tvPlaceOfMember?.text = ""
                    }
                }
                counterOfFilledMembers = 0
                for (i in 0 until arraySums.size) {
                    arraySums[i] = 0
                }
                delay(1)
            }
        }
    }
}