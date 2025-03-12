import math
import numpy as np
import scipy.stats as stats
import matplotlib.pyplot as plt
from matplotlib.backends.backend_pdf import PdfPages


with PdfPages("plots/exc2.pdf") as pdf:
    for n in [10, 15, 20, 25, 30, 50, 100, 200]:
        x = np.arange(0, n+1)
        cdf = stats.binom.cdf(x, n, .5) # trzeba jeszcze raz wygenerować bo wykresy są przesunięte
        E = stats.binom.mean(n, .5)
        var = stats.binom.var(n, .5)

        # Sn is a linear transformation of Bin(n, .5)
        tr_x = 2 * x - n
        tr_cdf = cdf # cdf y stays the same
        tr_E = 2 * E - n
        tr_var = 4 * var

        # Approximating with normal distribution
        norm_x = np.linspace(-n, n, 1000)
        norm_cdf = stats.norm.cdf(norm_x-1, tr_E, math.sqrt(tr_var))

        plt.figure()
        plt.title('N = ' + str(n))
        plt.bar(tr_x, tr_cdf, width=np.diff(tr_x, prepend=tr_x[0]), align='edge', color='limegreen', label='Sn CDF')
        plt.plot(norm_x, norm_cdf, color='royalblue', label='Normal distribution CDF')
        plt.ylim([0, 1])
        plt.xlabel('x')
        plt.ylabel('P(X <= x)')
        plt.legend()
        pdf.savefig()
        plt.close()
