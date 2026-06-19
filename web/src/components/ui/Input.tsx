"use client";

import React from "react";

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  icon?: React.ReactNode;
}

export const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className = "", label, error, icon, type = "text", ...props }, ref) => {
    return (
      <div className="flex flex-col gap-1.5 w-full">
        {label && (
          <label className="text-xs font-semibold uppercase tracking-wider text-zinc-400">
            {label}
          </label>
        )}
        <div className="relative flex items-center">
          {icon && (
            <div className="absolute left-3 text-zinc-500">
              {icon}
            </div>
          )}
          <input
            ref={ref}
            type={type}
            className={`w-full bg-zinc-950/80 border border-zinc-800 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 text-zinc-100 rounded-md py-2 px-3 text-sm transition-all duration-200 placeholder-zinc-600 outline-none ${
              icon ? "pl-10" : ""
            } ${error ? "border-rose-500/80 focus:border-rose-500 focus:ring-rose-500" : ""} ${className}`}
            {...props}
          />
        </div>
        {error && (
          <span className="text-xs text-rose-500/90 font-medium mt-0.5">{error}</span>
        )}
      </div>
    );
  }
);

Input.displayName = "Input";
