package median;

import imagefilter.ImageReadWrite;

public class Outlier {

    int nrows, ncols;
    int image1[][], image2[][];
    int totalOutliers = 0;

    public void process(String fileIn, String fileOut) {

        ImageReadWrite io = new ImageReadWrite();
        image1 = io.ImageRead(fileIn);

        nrows = image1.length;
        ncols = image1[1].length;

        image2 = new int[nrows][ncols];

        //--- Browse Source Image Matrix----//
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                image2[i][j] = median(i, j);
            }
        }
        io.ImageWrite(fileOut, image2);
        System.out.println("---------------------------------");
        System.out.println("Total Outliers: " + totalOutliers);
    }

    public int median(int i, int j) {
        int m, n, count, kernel[], tmp;

        //--- Browse Kernel Matrix ---//
        kernel = new int[9];
        for (m = i - 1, count = 0; m <= i + 1; m++) {
            for (n = j - 1; n <= j + 1; n++) {
                if (m >= 0 && m < nrows && n >= 0 && n < ncols) {
                    kernel[count++] = image1[m][n];
                }
            }
        }

        int sum=0;
        //--- Add All Kernel Values ---//
        for (m = 0; m < count - 1; m++) {
            sum += kernel[m];
        }

        /*------------------------------------------------------------------------------------------        
        For every pixel I(i, j)
            • Compute the average pixel value of the neighborhood, n(i, j)
            • If |I(i, j)- n(i, j)| > threshold then replace I(i, j) with the neighborhood average
            • Result will be sensitive to the selected threshold        
        ------------------------------------------------------------------------------------------*/
        double average = Math.abs(sum/9);
        double neighborhood = (sum-image1[i][j]) / 8;
        int result = image1[i][j] - (int)average;
        
        if (result > 50) {
            totalOutliers++;
            System.out.format("IMG=%3d ", image1[i][j]);            
            System.out.format("AVG=%.1f ", average);
            System.out.format("RES=%3d ", result);
            System.out.format("0=%3d ", kernel[0]);
            System.out.format("1=%3d ", kernel[1]);
            System.out.format("2=%3d ", kernel[2]);
            System.out.format("3=%3d ", kernel[3]);
            System.out.format("4=%3d ", kernel[4]);
            System.out.format("5=%3d ", kernel[5]);
            System.out.format("6=%3d ", kernel[6]);
            System.out.format("7=%3d ", kernel[7]);
            System.out.format("8=%3d ", kernel[8]);
            System.out.println();
            
            return (int)neighborhood;
            
        } else {
            return image1[i][j];
        }
    }
}
